package com.mee.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IOrderPayMapper;
import com.mee.manage.po.OrderPay;
import com.mee.manage.service.IOrderPayService;

import org.springframework.stereotype.Service;

@Service
public class OrderPayServiceImpl extends ServiceImpl<IOrderPayMapper, OrderPay> implements IOrderPayService {

    @Override
    public List<OrderPay> getOrderPay(Long orderId) {
        QueryWrapper<OrderPay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return list(queryWrapper);
    }

}