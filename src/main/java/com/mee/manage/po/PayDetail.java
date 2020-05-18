package com.mee.manage.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_pay_detail")
public class PayDetail {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    Long orderId;

    Integer feeType;

    @TableField(exist=false)
    String feeTypeName;

    BigDecimal price;
}