# xunyi-shop
一个关于衣服定制的商城
最近，花七天做了一个商城的项目，是一个服装定制商城，这个商城集合了我自己所学习的大部分技术。我先列一下这个商城用到的技术：

1. springmvc
2. thymeleaf
3. mybatis
4. webSecurity
5. WebSocket
6. rest风格
7. redis
8. docker

这个商城的前端是来自一位GitHub的开发者，而后端则是完全由自己完成的，在这个过程中，以我的角度来说，三分之二的时间都在写thymeleaf和Controller。尤其是在写的时候一遍又一遍的体会thymeleaf的各种语法，以及把DAO层的一些数据拿到Controller层进行转发，当然，都进行了REST风格化。而还有三分之一的时间在mybatis和Security身上，对mybatis的各种获取数据和Security的对各个页面的权限，其实最让我满意的就是这一部分了。

在这个项目过程中，我主要做对了一件事情，就是先进行了对Security的设计，而做错了很多事情，那就是没有先设计好数据库和页面，对页面跳转的各种方法，非常的粗糙，但这也让我领悟到了很关键的一层，一开始有一个对系统架构的完整设计，是多么重要的事情，我们接到一个需求的时候，就先应该设计好数据库表，在去对各种功能的完善。

做这个项目难吗？我觉得，并不难，也许是我才学浅，大部分的时间都浪费在了CRUD身上。

在一些技术的运用，例如Security，Socket，本人认为并没有什么差错，倒是简单的SpringMVC，运用的有些糟糕，但我也从中吸取到了很多教训，比如前端的很多请求，最好都使用POST表单提交，而少使用一些 直接定位的GET请求，而后端在控制器层最好少使用一些对数据库的采集，最好反复利用Session。

下面我开始简单的讲述一下，这个服装定制商城。

这个项目在设计之初，我就想到了要做前台和后台，这样的Security是必不可少的，我首先列出了有哪一些用户，然后将数据库的用户进行了分开的处理：

```java
      @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserModel user = userService.getUserName(s);
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        return new User(user.getUsername() ,user.getPassword(),
                createAuthority(user.getRoles()));

    }

//这里是将数据库的角色分割，构造GrantedAuthority
    private List<SimpleGrantedAuthority> createAuthority(String roles) {
        String[] roleArray = roles.split(",");
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        for (String role : roleArray) {
            authorityList.add(new SimpleGrantedAuthority(role));
        }
        return authorityList;
    }
```

基于Security的特性，权限都是：ROLE_USER 这样的类型，然后会 割开“_”，来选择权限

```java
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()//允许/、/login的访问
                .antMatchers("/user/**").hasRole("USER")//用户USER角色的用户访问有关/user下面的所有
                .antMatchers("/admin/**").hasRole("ADMIN")//同上
//                .anyRequest().authenticated()//其它所有访问都拦截
                .and()
                .formLogin()//添加登陆
                .loginPage("/login").permitAll()//登陆页面“/login"允许访问
                .defaultSuccessUrl("/")//成功默认跳转 url
                .usernameParameter("user")
                .passwordParameter("password")
                .permitAll()

                .and()
                .exceptionHandling().accessDeniedPage("/error");

        http.rememberMe();
        http.logout().logoutSuccessUrl("/");

    }
```

有着Security的帮助，从某种程度而言，也是一个架构的雏形呢，起码一个CRUD操作，不会越写越乱。

然后，就是把图片与本地的数据库分离，进一步降低数据库的压力：

```java
    /**
     * 华南机房,配置自己空间所在的区域
     */
    @Bean
    public com.qiniu.storage.Configuration qiniuConfig() {
        return new com.qiniu.storage.Configuration(Zone.zone2());
    }

    /**
     * 构建一个七牛上传工具实例
     */
    @Bean
    public UploadManager uploadManager() {
        return new UploadManager(qiniuConfig());
    }

    /**
     * 认证信息实例
     * @return
     */
    @Bean
    public Auth auth() {
        return Auth.create(qiniuProperties.getAccessKey(), qiniuProperties.getSecretKey());
    }

    /**
     * 构建七牛空间管理实例
     */
    @Bean
    public BucketManager bucketManager() {
        return new BucketManager(auth(), qiniuConfig());
    }

    @Bean
    public Gson gson() {
        return new Gson();
    }
```

控制器：

```java
    /**
     * 以流的形式上传图片
     *
     * @param file
     * @return 返回访问路径
     * @throws IOException
     */
    @PostMapping("upload")
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return qiniuService.uploadFile(file.getInputStream());
    }

    /**
     * 删除文件
     *
     * @param key
     * @return
     * @throws IOException
     */
    @GetMapping("delete/{key}")
    public Response deleteFile(@PathVariable String key) throws IOException {
        return qiniuService.delete(key);
    }
```

在这方面，有着各自的接口是辅助实现。

然后，是在思考如何生成一个全局唯一的ID，那时候想了很久，最后在网络上找到了，雪花算法。

并且，算是会熟练的使用MyBatis写SQL语句了吧。下面列一些拙劣的操作：

```xml
    <!-- 展示产品 -->
	<select id="showProduct" resultType="product">
		select * from product_table
	</select>
<!--	获取单个产品-->
	<select id="getProduct" resultType="product">
		select * from product_table where id=#{id}
	</select>

	<!--	获取图片群-->
	<select id="getimgs" resultType="item">
		select * from item_table where id=#{id}
	</select>

<!--增加产品	-->
	<insert id="addProduct" useGeneratedKeys="true" keyProperty="id"
			parameterType="product">
		insert into product_table(product_name, stock,price,version,note,img)
		values(#{productName},#{stock},#{price},#{version},#{note},#{img})
	</insert>

    <!-- 减少产品 -->
	<delete id="deleteProduct" parameterType="long">
		delete from product_table where id = #{id}
	</delete>

<!--	搜索功能  =-->
	<select id="searchProduct" resultType="product">
		select * from product_table where product_name LIKE CONCAT('%',#{productname},'%' )
	</select>
```

当然，这些操作并没有多么的出彩，甚至可以说是污点。

当然，我也有很多自以为是的骚操作：

比如，实现聊天信息的字符串拼接，每当有一句话被写回到后端时，不急于写进数据库，而是将它们给拼进session中，等聊天结束了，再某一个页面，写回数据库：

```java
        if (session.getAttribute("msg")==null){
            session.setAttribute("msg","");
        }

        String text=username+":"+msg+"。"+"\n";

        session.setAttribute("msg",session.getAttribute("msg")+text);
```

当然本人也有自己的职业操守的，比如@PathVariable这个注解，就使用了很多，时刻注意的规范。

同时，也伴随着一些失败的操作，比如说：

```javascript
location.href = '/user/design/'+[[${product.getId()}]];
```

过多的使用了直接定位，这使得网页的传参变得不那么安全。

以及为了急于放行，对数据库表的设计，非常的混乱，这也是必须注意的一个点。虽然它也就做了七天。

最后，再放一下这个项目的源码吧：
