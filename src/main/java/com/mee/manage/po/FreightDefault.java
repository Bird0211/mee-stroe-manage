package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_freight_default")
public class FreightDefault {
    Long id;

    Long bizId;

    Long freightId;
}