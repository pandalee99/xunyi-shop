package com.example.sodse.controller;

import com.example.sodse.pojo.*;
import com.example.sodse.server.ProductService;
import com.example.sodse.server.UserService;
import com.example.sodse.snow.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@RequestMapping("/user")
public class UserController  {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    //用户的订单和聊天信息
    //这个是订单
    @RequestMapping("/order")
    public String order(Model model,HttpSession session){

        UserModel userModel= userService.getUserName(getUsername());

        model.addAttribute("user",userModel);


        return "user/order";
}

    @RequestMapping("/pay/{money}/{l1}/{l2}/{l3}")
    public String pay(@PathVariable("money") String money,
                      @PathVariable("l1") String l1
            , @PathVariable("l2") String l2
            , @PathVariable("l3") String l3
            , Model model, HttpSession httpSession){
        model.addAttribute("money",Double.parseDouble(money));
        model.addAttribute("user",userService.getUserName(getUsername()));

        Shipping_address address=new Shipping_address();
        address.setAddress(l1);
        address.setName(l2);
        address.setPhone(l3);
        httpSession.setAttribute("address",address);

        //发起这个请求
        IdWorker id=new IdWorker(1,1);
        //建立唯一ID
        Long x=id.nextId();
        //将唯一ID写List当中，并将List加入Session
        List<Long> IdList=new ArrayList<>();
        IdList.add(x);
        httpSession.setAttribute("Uid",IdList);

        //写入订单表，处于未支付状态
        Order order=new Order();
        order.setId(x);
        Product product=(Product)httpSession.getAttribute("check_product");
        order.setProductName(product.getProductName());
        order.setPrice(product.getPrice());
        order.setAmount((Long)httpSession.getAttribute("amount"));
        order.setTotal(Double.parseDouble(money));
        order.setImg(product.getImg());
        order.setUsername(getUsername());
        order.setAddress(l1);
        order.setCategory(product.getCategory());
        //时间：
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());

        String date= sdf.format(calendar.getTime());
        //
        order.setOtime(date);
        order.setStatus("未支付");
        //设计
        order.setDesign((String) httpSession.getAttribute("msg"));

        //不改变用户金额
        productService.addorder(order);

        return "user/pay";

    }
    @RequestMapping("/paycar/{total}")
    public String paycar(@PathVariable("total") String total,Model model,HttpSession session){


        UserModel userModel=userService.getUserName(getUsername());
        model.addAttribute("money",Double.parseDouble(total));
        model.addAttribute("user",userModel);

        Shipping_address address=new Shipping_address();
        address.setAddress(userModel.getAddress());
        address.setName(userModel.getName());
        address.setPhone(userModel.getPhone());
        session.setAttribute("address",address);

        //加入购物车后，发起这个请求：
        //从Session中获取列表
        List<Car_temp> products=(List<Car_temp>) session.getAttribute("session_product");
        for (Car_temp temp:products) {
            //为每一件商品名称建立唯一ID
            IdWorker id=new IdWorker(1,1);
            //建立唯一ID
            Long x=id.nextId();
            //将唯一ID写List当中，并将List加入Session
            List<Long> IdList=new ArrayList<>();
            IdList.add(x);
            session.setAttribute("Uid",IdList);
            //将唯一ID写入List，并将其加入Session

            Order order=new Order();
            order.setId(x);

            order.setProductName(temp.getProductname().getProductName());
            order.setPrice(temp.getProductname().getPrice());
            order.setAmount(temp.getAmount());
            order.setTotal(temp.getProductname().getPrice()*temp.getAmount());
            order.setImg(temp.getProductname().getImg());
            order.setUsername(getUsername());
            order.setAddress(address.getAddress());
            order.setCategory(temp.getProductname().getCategory());
            //时间：
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(new Date());
            String date= sdf.format(calendar.getTime());
            //
            order.setOtime(date);
            order.setStatus("未支付");
            //设计
            order.setDesign((String) session.getAttribute("msg"));

            //删除所有购物车

            userService.deleteallcar(getUsername());

            // 写入订单表，但是处于未支付状态，
        productService.addorder(order);
        //不改变用户的金额
    }
        return "user/pay";
    }

    @RequestMapping("/success/{pay}")
    public String success(@PathVariable("pay") int pay,Model model,HttpSession session){

        model.addAttribute("pay",pay);

        model.addAttribute("user",userService.getUserName(getUsername()));

        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,3);
        String date= sdf.format(calendar.getTime());

        model.addAttribute("date",date);

        //从Session中提取List，
        List<Long> longs=(List<Long>) session.getAttribute("Uid");
        // 根据唯一ID，将订单表状态写入为已支付
        for (Long id:longs) {
        productService.updateorder(id,"待发货");
        }
        //根据传入金额，修改用户剩余金额

        return "user/success";

    }

    @RequestMapping("/car")
    public String car(Model model,HttpSession session){

        List<Car>  cars= userService.findcar(getUsername());

        List<Car> carList = new ArrayList<Car>();

        for(int i = 0; i < cars.size(); i++){
            Long key = cars.get(i).getId();
            Long value = cars.get(i).getAmount();
            for(int j = i+1; j < cars.size();j++){
                if(key.equals(cars.get(j).getId())){
                    value  += cars.get(j).getAmount();
                    //遍历完成后，要删除car的id相同的数据
                    cars.remove(cars.get(j));
                    //remove一个元素时，要把遍历的指针减一
                    j--;
                }
            }

            carList.add(new Car(cars.get(i).getId(),value));
        }

        List<Car_temp> temps=new ArrayList<>();

        for (Car c:carList) {
            Car_temp carTemp=new Car_temp();

            carTemp.setProductname(productService.get(c.getId()));

            carTemp.setAmount(c.getAmount());
            temps.add(carTemp);
        }

        model.addAttribute("product",temps);
        session.setAttribute("session_product",temps);


        return "user/car";
    }


    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        userService.deletecar(getUsername(),id);

        return "redirect:/user/car";
    }


    @RequestMapping("/design/{id}")
    public String design(@PathVariable("id") Long id ,Model model){
        model.addAttribute("user",getUsername());
        model.addAttribute("id",id);

        return "chat";
    }

    @RequestMapping("/my")
    public String my(Model model){
        List<Order> orders=productService.findorder(getUsername());

        //未支付队列
        List<Order> unpaid=new ArrayList<>();
        //已支付队列
        List<Order> paid=new ArrayList<>();

        for (Order order:orders) {
            if (order.getStatus().equals("未支付")){
                    unpaid.add(order);
            }
            if (order.getStatus().equals("待发货")){
                paid.add(order);
            }
        }

        model.addAttribute("unpaid",unpaid);
        model.addAttribute("paid",paid);
        return "user/my";
    }

    @RequestMapping("/mypay/{money}/{id}")
    public String mypay(@PathVariable("money") String money,@PathVariable("id") Long id,Model model,HttpSession session){
        UserModel userModel=userService.getUserName(getUsername());
        model.addAttribute("money",Double.parseDouble(money));
        model.addAttribute("user",userModel);


        Shipping_address address=new Shipping_address();
        address.setAddress(userModel.getAddress());
        address.setName(userModel.getName());
        address.setPhone(userModel.getPhone());
        session.setAttribute("address",address);
        List<Long> uid=new ArrayList();
        uid.add(id);
        session.setAttribute("Uid",uid);

        return "user/pay";
    }





    private String getUsername() {
        String usename= SecurityContextHolder.getContext().getAuthentication().getName();
        return usename;
    }

    /*
    获取当前用户权限
     */
    private String getAuthority() {
        //获取 Authentication 对象，表示用户认证信息
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        List<String> roles=new ArrayList<String>();
        //将角色名称添加到List集合
        for (GrantedAuthority a:authentication.getAuthorities()){
            roles.add(a.getAuthority());
        }
        System.out.println("role="+roles);
        return roles.toString();
    }



}
