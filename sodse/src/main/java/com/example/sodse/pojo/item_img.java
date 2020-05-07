package com.example.sodse.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("item")
public class item_img {

    private Long id;

    private String img;

}
