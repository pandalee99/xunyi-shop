package com.example.sodse.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("order")
public class Order {

    private Long id;

    private String productName;

    private double price;

    private Long amount;

    private double total;

    private String img;

    private String username;

    private String address;

    private String category;

    private String otime;

    private String status;

    private String design;


}
