package com.mee.manage.vo;

import java.util.Date;
import java.util.List;

import com.mee.manage.po.OrderPay;
import com.mee.manage.po.PayDetail;

import lombok.Data;

@Data
public class OrderPayVo {

    Date payTime;
    
    List<OrderPay> orderPay;

    List<PayDetail> payDetails;

}