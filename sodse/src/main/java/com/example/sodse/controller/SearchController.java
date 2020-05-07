package com.example.sodse.controller;

import com.example.sodse.pojo.Product;
import com.example.sodse.server.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class SearchController {

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public String search(String obj, Model model){


        List<Product> products= productService.searchProduct(obj);

        model.addAttribute("products",products);

        return "search";
    }

}
