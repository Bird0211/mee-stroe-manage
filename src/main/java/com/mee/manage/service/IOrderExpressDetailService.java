package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.OrderExpressDetail;

public interface IOrderExpressDetailService extends IService<OrderExpressDetail> {

    List<OrderExpressDetail> getExpressDetails(List<Long> expressIds);

    List<OrderExpressDetail> getExpressDetail(Long expressId);

}