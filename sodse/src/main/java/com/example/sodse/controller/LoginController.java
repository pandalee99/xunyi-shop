package com.example.sodse.controller;

import com.example.sodse.pojo.UserModel;
import com.example.sodse.server.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    @Autowired
    UserServiceImpl userService;

    @GetMapping("/")
    public String index(){
        return "redirect:/shop/index";
    }

    @RequestMapping("/login")
    public String login( ){

        return "login";
    }

    @RequestMapping("/register")
    public String register(Model model){
        UserModel userModel=new UserModel();
        model.addAttribute("user",userModel);
        return "register";
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
    public String addUser(@ModelAttribute UserModel user){
        userService.addUser(user);

        return "redirect:/login";
    }

    @RequestMapping("/checkname/{user}")
    @ResponseBody
    public String checkname(@PathVariable("user") String user){
        String findname=userService.findname(user);
        if (findname!=null){
            return "error";
        }
        else {
            return  "success";
        }
    }

    @RequestMapping("/logout")
    public String logoutpage(HttpServletRequest request, HttpServletResponse response){
        //Authentication 是一个接口，用来表示用户认证信息
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if (auth!=null){
            new SecurityContextLogoutHandler().logout(request,response,auth);
        }

        //重定向到 login
        return "redirect:/";

    }
}
