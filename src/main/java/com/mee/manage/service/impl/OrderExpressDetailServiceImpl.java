package com.mee.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IOrderExpressDetailMapper;
import com.mee.manage.po.OrderExpressDetail;
import com.mee.manage.service.IOrderExpressDetailService;

import org.springframework.stereotype.Service;

@Service
public class OrderExpressDetailServiceImpl extends ServiceImpl<IOrderExpressDetailMapper, OrderExpressDetail>
        implements IOrderExpressDetailService {

    @Override
    public List<OrderExpressDetail> getExpressDetails(List<Long> expressIds) {
        QueryWrapper<OrderExpressDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("express_id", expressIds);
        return list(queryWrapper);
    }

    @Override
    public List<OrderExpressDetail> getExpressDetail(Long expressId) {
        QueryWrapper<OrderExpressDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("express_id", expressId);
        return list(queryWrapper);
    }

}