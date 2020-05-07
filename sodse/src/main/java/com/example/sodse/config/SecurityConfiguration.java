package com.example.sodse.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    MyUserDetailService myUserDetailService;

//    @Autowired
//    AuthenticationSuccessHandler authenticationSuccessHandler;

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
                //设置注销操作
                //所有用户都可以访问（可以使用注销）
                .and()
                .exceptionHandling().accessDeniedPage("/error");
//使用cookies
//        http.authorizeRequests()
//                .and()
//                .rememberMe()
//                .tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(60)
//                .userDetailsService(myUserDetailService);
//使用数据库
//                .and()
//                .rememberMe()
//                .key("uniqueAndSecret")
//                .rememberMeCookieName("remember-me")
//                .tokenValiditySeconds(60);
        http.rememberMe();
        http.logout().logoutSuccessUrl("/");

    }

//    /**
//     * 配置地址栏不能识别 // 的情况
//     * @return
//     */
//    @Bean
//    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
//        StrictHttpFirewall firewall = new StrictHttpFirewall();
//        //此处可添加别的规则,目前只设置 允许双 //
//        firewall.setAllowUrlEncodedDoubleSlash(true);
//        return firewall;
//    }


//    @Bean
//    public PersistentTokenRepository persistentTokenRepository() {
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        // 配置数据源
//        jdbcTokenRepository.setDataSource(dataSource);
//        // 第一次启动的时候自动建表（可以不用这句话，自己手动建表，源码中有语句的）
//         //jdbcTokenRepository.setCreateTableOnStartup(true);
//        return jdbcTokenRepository;
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(myUserDetailService)
                .passwordEncoder(new MyPassWordEncoder());
    }


//
//    @Autowired
//    AuthenticationSuccessHandler authenticationSuccessHandler;
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        System.out.println("AppSecurityConfigurer()调用-------");
//        http.authorizeRequests()
//                //spring-scurity 5.0之后需要过滤静态资源
//
//                .antMatchers("/login","/css/**","/js/**","/img/**").permitAll()
//                //指定用户可以访问的多个url模式。
//                // 特别的，任何用户可以访问以"/resources"开头的url资源，或者等于"/signup"或about
//
//                .antMatchers("/","/home").hasRole("USER")
//                //任何以"/home"开头的请求限制用户具有 "ROLE_USER"角色。
//                // 你可能已经注意的，尽管我们调用的hasRole方法，但是不用传入"ROLE_"前缀
//
//
//                .antMatchers("/admin/**").hasAnyRole("ADMIN","DBA")
//                //这个是拥有其中一个权限都能使用
//
//                .anyRequest().authenticated()
//                //任何没有匹配上的其他的url请求，需要用户被验证。
//
//                .and()
//                //拼接
//
//                .formLogin().loginPage("/login")
//                //开始设置登录操作
//                //设置登录页面的访问地址
//
//                .successHandler(authenticationSuccessHandler)
//                //设置了一个认证处理，登录成功后不同用户需要跳转到不同的页面
//                //以此认证，视为通行证
//
//                .usernameParameter("loginName").passwordParameter("password")
//                //登录时接受传递的参数 loginName 和password
//                //注意大小写
//
//                .and()
//                .logout().permitAll()
//                //设置注销操作
//                //所有用户都可以访问（可以使用注销）
//
//                .and()
//                .exceptionHandling().accessDeniedPage("/accessDenied");
//        //指定异常处理页面
//        //特别是在没有权限的时候使用发生的错误
//    }
}
