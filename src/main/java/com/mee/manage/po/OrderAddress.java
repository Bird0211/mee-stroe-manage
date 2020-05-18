package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_order_address")
public class OrderAddress {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    Long orderId;

    String city;

    String street;

    String firstName;

    String lastName;

    String phone;

    String postcode;

    String email;

    String suburb;

}