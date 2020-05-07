package com.example.sodse.server;


import com.example.sodse.pojo.Order;
import com.example.sodse.pojo.Product;
import com.example.sodse.pojo.item_img;

import java.util.List;

public interface ProductService {
    //展示全部

    List<Product> ShowAll();

    //删除产品

    int delete(Long id);

    //增加产品

     int add(Product product);

     Product get(Long id);

    List<item_img> getimgs(Long id);

    //订单
    void addorder(Order order);

    //显示订单

    List<Order> findorder(String name);

    List<Product> searchProduct(String productname);


    //更改订单状态
    void updateorder(Long id,String status);

}
