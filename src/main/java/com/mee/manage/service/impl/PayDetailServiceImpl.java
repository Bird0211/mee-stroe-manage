package com.mee.manage.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IPayDetailMapper;
import com.mee.manage.po.PayDetail;
import com.mee.manage.service.IPayDetailService;

import org.springframework.stereotype.Service;

@Service
public class PayDetailServiceImpl extends ServiceImpl<IPayDetailMapper, PayDetail> implements IPayDetailService {

    @Override
    public List<PayDetail> getPayDetails(Long orderId) {
        QueryWrapper<PayDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        return list(queryWrapper);
    }

    @Override
    public boolean addPayDetails(List<PayDetail> payDetails) {
        return saveBatch(payDetails);
    }
    

}