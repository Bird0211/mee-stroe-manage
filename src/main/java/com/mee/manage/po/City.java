package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_city")
public class City {

    @TableId(type = IdType.AUTO)
    Long id;

    String city;

    String suburb;

    Integer sort;
}