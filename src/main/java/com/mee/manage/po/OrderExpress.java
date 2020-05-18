package com.mee.manage.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mee.manage.enums.ExpressCompanyEnum;

import lombok.Data;

@Data
@TableName("t_app_order_express")
public class OrderExpress {

    @TableId(type = IdType.ID_WORKER)
    Long id;

    Long orderId;

    String expressCode;

    String expressName;

    /**
     * 0：全部发货；2:拆单
     */
    Integer type;

    @TableField(exist = false)
    String expressCompanyName;

    public String getExpressCompanyName() {
        return ExpressCompanyEnum.getCompanyEnum(this.expressName);
    }
}