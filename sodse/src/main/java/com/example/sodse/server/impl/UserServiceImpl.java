package com.example.sodse.server.impl;

import com.example.sodse.dao.UserMapper;
import com.example.sodse.pojo.Car;
import com.example.sodse.pojo.UserModel;
import com.example.sodse.server.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserModel getUserName(String name) {
        return userMapper.getUser(name);
    }

    @Override
    public void addUser(UserModel user) {
         userMapper.addUser(user);
    }

    @Override
    public String findname(String name) {
        return userMapper.findname(name);
    }

    @Override
    public void addCar(Car car) {
        userMapper.addCar(car);
    }

    @Override
    public List<Car> findcar(String username) {
        return userMapper.findcar(username);
    }

    @Override
    public void deletecar(String name, Long id) {
        userMapper.deletecar(name,id);
    }

    @Override
    public void deleteallcar(String name) {
        userMapper.deleteallcar(name);
    }
}
