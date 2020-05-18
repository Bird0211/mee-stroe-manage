package com.mee.manage.po;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_order")
public class Order {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    Long userId;

    Integer status;

    Date createTime;

    Date payTime;

    Long bizId;

    BigDecimal totalPrice;

    String remark;

}