package com.mee.manage.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_order_pay")
public class OrderPay {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    Integer payCode;

    @TableField(exist=false)
    String payCodeName;

    BigDecimal payPrice;

    Long orderId;

    String reference;

    String payNo;

}