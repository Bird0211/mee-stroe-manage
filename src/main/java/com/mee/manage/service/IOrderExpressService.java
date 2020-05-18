package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.OrderExpress;

public interface IOrderExpressService extends IService<OrderExpress> {

    public boolean save(Long orderId, String expressNo, String expressName);

    public List<OrderExpress> getOrderExpressByOrderId(Long orderId);

}