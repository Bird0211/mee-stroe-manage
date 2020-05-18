package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

@Data
@TableName("t_app_order_express_detail")
public class OrderExpressDetail {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    Long expressId;

    Long orderDetail;

    Integer number;
}