package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.OrderPay;

public interface IOrderPayService extends IService<OrderPay>{
    List<OrderPay> getOrderPay(Long orderId);
}