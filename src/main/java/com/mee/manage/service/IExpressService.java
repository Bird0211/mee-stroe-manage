package com.mee.manage.service;

import java.util.List;

import com.mee.manage.po.OrderDetail;
import com.mee.manage.po.OrderExpress;
import com.mee.manage.vo.ExpressCompanyVo;

public interface IExpressService {
    
    List<ExpressCompanyVo> getExpressCompany(Long bizId);

    List<OrderExpress> getExpress(Long orderId);

    List<OrderDetail> getUnDelivery(Long orderId);

    List<OrderDetail> getExpressDetail(Long expressId);

}