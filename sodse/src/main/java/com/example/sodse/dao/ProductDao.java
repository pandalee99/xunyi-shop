package com.example.sodse.dao;


import com.example.sodse.pojo.Product;
import com.example.sodse.pojo.item_img;

import java.util.List;

public interface ProductDao {
    // 展示产品

     List<Product> showProduct();
    //删除产品
     int deleteProduct(Long id);

    //增加产品
     int addProduct(Product product);

     //获取单个产品
    Product getProduct(Long id);

    List<item_img> getimgs(Long id);

    //搜索产品
    List<Product> searchProduct(String productname);
}
