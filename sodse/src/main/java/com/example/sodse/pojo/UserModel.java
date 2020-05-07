package com.example.sodse.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@Data
@Alias("user")
public class UserModel implements Serializable {

    private String username;

    private String password;

    private String roles;

    private String name;

    private int money;

    private String address;

    private String phone;


}
