package com.example.sodse;


import com.example.sodse.pojo.Product;
import com.example.sodse.server.ProductService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class AddData {

    @Autowired
    ProductService productService;

    @Test
    void add(){

        String q="鞋子";
        String url="http://www.shihuo.cn/running/list?page_size=60&scene=跑步鞋&sort=hot&c=跑鞋#qk=shaixuan";

        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }
        //这里根据在网页中分析的类选择器来获取列表所在的节点
        Elements div = doc.getElementsByClass("list-main");
        Elements ul = div.select("ul.list-ul");
        Elements lis = ul.select("li");//查找标签

        int count=0;
        for (Element li : lis) {
            try {
                Thread.sleep(500);//让线程操作不要太快  时间自己设置，主要是模拟人在点击
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取所有详情的链接所在的节点
            Product product=new Product();

            //设置图片
            Elements img=li.select("img");
            String imgUrl = img.get(0).attr("abs:src");
            product.setImg(imgUrl);

            //设置名字
            Elements title=li.select("a");
            product.setProductName(title.get(1).text());

            //设置价格
            String price=li.select("b").get(0).text();
            product.setPrice(Double.parseDouble(price.substring(1,price.length())));

            //设置类别
            product.setCategory(q);

            //
            product.setNote("一双"+q);
            product.setStock(100);

            productService.add(product);

            count++;
            if (count==50){
                break;
            }


        }


    }
}
