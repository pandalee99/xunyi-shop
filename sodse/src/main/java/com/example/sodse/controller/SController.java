package com.example.sodse.controller;


import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class SController {

    @RequestMapping("/receive/{user}/{msg}")
    public void receive(@PathVariable("user") String username, @PathVariable("msg") String msg , HttpSession session){

        if (session.getAttribute("msg")==null){
            session.setAttribute("msg","");
        }

        String text=username+":"+msg+"。"+"\n";

        session.setAttribute("msg",session.getAttribute("msg")+text);

        //将信息，写入Session当中
    }

}
