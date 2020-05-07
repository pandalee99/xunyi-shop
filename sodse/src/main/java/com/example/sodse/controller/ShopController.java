package com.example.sodse.controller;

import com.example.sodse.pojo.Car;
import com.example.sodse.pojo.Product;

import com.example.sodse.server.ProductService;
import com.example.sodse.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    static String ip="http://images.sodse.com/";

    @RequestMapping("/index")
    public String index(Model model){
        List<Product> products=productService.ShowAll();

        List<Product> Clothes=new ArrayList<>();
        List<Product> Shoes=new ArrayList<>();

        for (Product c:
             products) {
            if(c.getCategory().equals("衣服")){
                Clothes.add(c);
            }
            if(c.getCategory().equals("鞋子")){
                Shoes.add(c);
            }
        }

        model.addAttribute("Clothes",Clothes);
        model.addAttribute("Shoes",Shoes);

        return "index";
    }

    @RequestMapping("/single_product/{p}")
    public String single(@PathVariable("p") Long id, Model model){

        model.addAttribute("product",productService.get(id));

        model.addAttribute("images",productService.getimgs(id));


        return "item";
    }

    @RequestMapping("/single_product/check/{p}/{s}")
    public String check(@PathVariable("p") Long id,@PathVariable("s") Long amount,HttpSession session){


        session.setAttribute("check_product",productService.get(id));
        session.setAttribute("amount",amount);

        return "redirect:/user/order";
    }


    @RequestMapping("/addcar/{amount}/{id}")
    @ResponseBody
    public String addcar(@PathVariable("amount") String amount,@PathVariable("id") String id,HttpSession session){

        session.setAttribute("amount",amount);
        session.setAttribute("product_id",id);

        if (getUsername().equals("anonymousUser")){
            return "no";
        }
        else if (false){
            return "error";
        }
        else {

            //写入购物车表
            //购物车表有用户名，产品信息，和数量
            //
            Car car=new Car(1L,1);
            car.setId(Long.parseLong(id));
            car.setAmount(Long.parseLong(amount));
            car.setUsername(getUsername());
            userService.addCar(car);
            return  "success";
        }

    }

    @RequestMapping("/my_shopping_cart")
    public String my_shopping_cart(){

        return "redirect:/user/car";
    }

    @RequestMapping("/my")
    public String my(){

        return "redirect:/user/my";
    }


    private String getUsername() {


       String usename= SecurityContextHolder.getContext().getAuthentication().getName();
        return usename;
    }

}
