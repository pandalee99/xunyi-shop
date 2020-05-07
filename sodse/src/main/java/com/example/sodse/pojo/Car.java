package com.example.sodse.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("car")
public class Car {

    Long id;

    public Car(Long id, long amount) {
        this.id = id;
        this.amount = amount;
    }

    Long amount;

    String  username;
}
