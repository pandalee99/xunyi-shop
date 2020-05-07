package com.example.sodse.server;

import com.example.sodse.pojo.Car;
import com.example.sodse.pojo.UserModel;

import java.util.List;

public interface UserService {
    //获取单个用户信息
    UserModel getUserName(String name);
//增加用户
    void addUser(UserModel user);
    //查找用户名是否存在
    String findname(String name);

    //增加购物车
    void addCar (Car car);

    //搜索购物车
    List<Car> findcar(String username);

    void deletecar(String name,Long id);

    void deleteallcar(String name);
}
