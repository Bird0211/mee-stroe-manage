package com.mee.manage.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_order_detail")
public class OrderDetail {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    Long orderId;

    Long productId;

    String productName;

    Long specId;

    String specName;

    Integer number;

    BigDecimal price;

    Long weight;

    @TableField(exist = false)
    String image;

    @TableField(exist = false)
    String barcode;

}