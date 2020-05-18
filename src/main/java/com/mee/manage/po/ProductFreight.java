package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_product_freight")
public class ProductFreight {
    
    @TableId(type = IdType.ID_WORKER)    
    Long id;

    Long productId;

    Long freightId;
}