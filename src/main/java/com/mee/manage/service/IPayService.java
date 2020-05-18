package com.mee.manage.service;

import java.util.List;

import com.mee.manage.vo.OrderPayVo;
import com.mee.manage.vo.PayMethodVo;

public interface IPayService {
    
    List<PayMethodVo> getMethod(Long bizId);

    OrderPayVo getOrderPayInfo(Long orderId);

}