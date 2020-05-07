package com.example.sodse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.example.sodse.dao")
public class SodseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SodseApplication.class, args);
    }

}
