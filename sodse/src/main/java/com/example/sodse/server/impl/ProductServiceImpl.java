package com.example.sodse.server.impl;


import com.example.sodse.dao.OrderDao;
import com.example.sodse.dao.ProductDao;
import com.example.sodse.pojo.Order;
import com.example.sodse.pojo.Product;
import com.example.sodse.pojo.item_img;
import com.example.sodse.server.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    OrderDao orderDao;

    @Override
    public List<Product> ShowAll() {
        return productDao.showProduct();
    }

    @Override
    public int delete(Long id) {
        return productDao.deleteProduct(id);
    }

    @Override
    public int add(Product product) {
        return productDao.addProduct(product);
    }

    //实验Redis
    @Override
    @Cacheable(value = "redisCache", key = "'redis_product_'+#id")
    public Product get(Long id) {
        return productDao.getProduct(id);
    }

    @Override
    public List<item_img> getimgs(Long id) {

        return productDao.getimgs(id);
    }

    @Override
    public void addorder(Order order) {
        orderDao.addorder(order);
    }

    @Override
    public List<Order> findorder(String name) {
        return orderDao.findorder(name);
    }

    @Override
    public List<Product> searchProduct(String productname) {
        return productDao.searchProduct(productname);
    }

    @Override
    public void updateorder(Long id, String status) {
        orderDao.updateorder(id,status);
    }
}
