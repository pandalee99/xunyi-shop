package com.example.sodse;

import com.example.sodse.pojo.Car;
import com.example.sodse.server.UserService;
import com.example.sodse.snow.IdWorker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SodseApplicationTests {

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {
        IdWorker a=new IdWorker(1,1);
        for (int i = 0; i <10 ; i++) {
            System.out.println(a.nextId());
        }
    }

    @Test
    void time(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(new Date());

        calendar.add(calendar.DATE,3);

        String date2= sdf.format(calendar.getTime());

        System.out.println(date2);

    }

    @Test
    void car(){

        HashMap map=new HashMap();

        List<Car>  cars= userService.findcar("张三");

        List<Car> userList = new ArrayList<Car>();

        for(int i = 0; i < cars.size(); i++){
            Long tmp = cars.get(i).getId();
            Long k = cars.get(i).getAmount();
            for(int j = i+1; j < cars.size();j++){
                if(tmp.equals(cars.get(j).getId())){
                    k += cars.get(j).getAmount();
                    //遍历完成后，要删除User的name相同的数据
                    cars.remove(cars.get(j));
                    //remove一个元素时，要把遍历的指针减一
                    j--;
                }
            }

            userList.add(new Car(cars.get(i).getId(),k));
        }

        for(int i = 0;i<userList.size();i++){
            System.out.print(userList.get(i).getId() + " ");
            System.out.print(userList.get(i).getAmount());
            System.out.println();
        }

    }


}
