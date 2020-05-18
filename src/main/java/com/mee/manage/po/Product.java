package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_app_product")
public class Product {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    private String name;

    private String description;

    private String image;
    
    private Long categoryId;

    private String categoryName;

    private Integer status;

    private Long brandId;

    private String brandName;

    private Long bizId;

}
