package com.mee.manage.vo;

import java.util.List;

import com.mee.manage.po.OrderExpress;
import com.mee.manage.po.OrderExpressDetail;

import lombok.Data;

@Data
public class PartialDeliveryVo {

    OrderExpress express;
    List<OrderExpressDetail> expressDetails;
}