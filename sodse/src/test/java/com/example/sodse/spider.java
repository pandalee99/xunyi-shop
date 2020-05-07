package com.example.sodse;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class spider {

    @Test
    void test() throws IOException {
//     douban("https://movie.douban.com/chart");
     shihuo("http://www.shihuo.cn/freestyle/list?scene=服饰&c=T恤#qk=catalog");
    }

    @Bean
    public void douban(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }
        //这里根据在网页中分析的类选择器来获取电影列表所在的节点
        Elements div = doc.getElementsByClass("indent");
        Elements table = div.select("table");//查找table标签
        for (Element tb : table) {
            try {
                Thread.sleep(200);//让线程操作不要太快
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取所有电影详情的链接所在的节点
            Elements tr = tb.select("tr");
            Elements img=tr.select("img");
            String imgUrl = img.get(0).attr("abs:src");
            System.out.println(imgUrl);
        }


    }

    @Bean
    public void shihuo(String url){
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
        for (Element li : lis) {
            try {
                Thread.sleep(200);//让线程操作不要太快 1秒一次 时间自己设置，主要是模拟人在点击
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取所有详情的链接所在的节点
            Elements img=li.select("img");
            String imgUrl = img.get(0).attr("abs:src");
            System.out.println(imgUrl);
        }


    }

    @Bean
    public int pachong_page(String url) throws IOException {
        //String url="http://www.ygdy8.net/html/gndy/dyzz/list_23_1.html";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).userAgent("Mozilla").get();//模拟火狐浏览器
        } catch (IOException e) {
            e.printStackTrace();
        }
        //这里根据在网页中分析的类选择器来获取电影列表所在的节点
        Elements div = doc.getElementsByClass("indent");
        //获取电影列表
        Elements table = div.select("table");//查找table标签
        //获取电影列表总数
        int result = table.size();
        //System.out.println("电影列表数为:"+result);
        for (Element tb : table) {
            try {
                Thread.sleep(1000);//让线程操作不要太快 1秒一次 时间自己设置，主要是模拟人在点击
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //获取所有电影详情的链接所在的节点
            Elements tr = tb.select("tr");
            //System.out.println(tr.size());
            //获取电影列表链接和标题
            String videos = tr.get(1).select("a").attr("abs:href");
            System.out.println(tr.get(1).select("a").attr("abs:href") + "\t" + tr.get(1).select("a").text());
            //这里要跳过这个首页页面 否则会抛出异常
            if ("http://www.dytt8.net/html/gndy/jddy/index.html".equals(videos)) continue;
            //进如电影列表详情页面
            doc = Jsoup.connect(videos).userAgent("Mozilla").get();
            //获取到电影详情页面所在的节点
            Element div1 = doc.getElementById("Zoom");
            //获取电影描述
            //String des=div1.select("p").text();
            //System.out.println(div1.select("p").text());
            //获取封面图地址
            Elements select = div1.select("img[src$=.jpg]");
            String imgUrl = select.get(0).attr("abs:src");
            System.out.println(imgUrl);
            //获取下载地址
            System.out.println(div1.select("td").text());

            //存入数据库
//            try {
//                Db.use().insert(
//                        Entity.create("movie")
//                                .set("type", "最新电影")
//                                .set("title", tr.get(1).select("a").text())
//                                .set("imagesUrl", imgUrl)
//                                .set("videoUrl", div1.select("td").text())
//                                .set("date", new Date())
//                                .set("describe", div1.select("p").text())
//
//                );
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }

        }
        return result;

    }
}
