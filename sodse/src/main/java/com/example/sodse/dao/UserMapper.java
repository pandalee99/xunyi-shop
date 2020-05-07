package com.example.sodse.dao;


import com.example.sodse.pojo.Car;
import com.example.sodse.pojo.UserModel;

import java.util.List;

public interface UserMapper {

    UserModel getUser(String username);

    void addUser(UserModel user);

    //查找用户名是否存在
    String findname(String name);

    //写入购物车表
    void addCar (Car car);

    //搜索购物车
    List<Car> findcar(String username);

    //删除单个购物车
    void deletecar(String username,Long id);

    //支付成功，用户的所有删除购物车
    void deleteallcar(String username);


}
