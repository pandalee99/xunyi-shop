package com.example.sodse.dao;

import com.example.sodse.pojo.Order;

import java.util.List;

public interface OrderDao {

    void addorder(Order order);

    //显示用户的订单
    List<Order> findorder(String username);

    //更新订单
    void updateorder(Long id,String status);

}
