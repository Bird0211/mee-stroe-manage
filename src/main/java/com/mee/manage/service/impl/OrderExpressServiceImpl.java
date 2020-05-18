package com.mee.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IOrderExpressMapper;
import com.mee.manage.po.OrderExpress;
import com.mee.manage.service.IOrderExpressService;

import org.springframework.stereotype.Service;

@Service
public class OrderExpressServiceImpl extends ServiceImpl<IOrderExpressMapper, OrderExpress>
        implements IOrderExpressService {

    @Override
    public boolean save(Long orderId, String expressCode, String expressName) {
        OrderExpress orderExpress = new OrderExpress();
        orderExpress.setOrderId(orderId);
        orderExpress.setExpressName(expressName);
        orderExpress.setExpressCode(expressCode);
        orderExpress.setType(0);
        return save(orderExpress);
    }

    @Override
    public List<OrderExpress> getOrderExpressByOrderId(Long orderId) {
        QueryWrapper<OrderExpress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
    
        return list(queryWrapper);
    }



}