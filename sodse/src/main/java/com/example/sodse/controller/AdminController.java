package com.example.sodse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    //负责改动页面信息，包括商品的增删改查
    //首先需要一个主页，
     @RequestMapping("/index")
    public String index(){

         return "admin/index";
     }


}
