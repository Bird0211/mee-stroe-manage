package com.mee.manage.po;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_freight_detail")
public class FreightDetail {
    Long id;
    Long freightId;
    String citys;
    Integer first;
    BigDecimal firstPrice;
    Integer more;
    BigDecimal morePrice;
    Double freeShipping;
    Integer freeType;   //0：无;1：按件包邮；2：按价包邮
}