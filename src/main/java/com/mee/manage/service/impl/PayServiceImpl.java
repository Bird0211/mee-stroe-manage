package com.mee.manage.service.impl;

import java.util.List;

import com.google.common.collect.Lists;
import com.mee.manage.enums.FeeTypeEnum;
import com.mee.manage.enums.PayMethodEnum;
import com.mee.manage.exception.MeeException;
import com.mee.manage.po.Order;
import com.mee.manage.po.OrderPay;
import com.mee.manage.po.PayDetail;
import com.mee.manage.service.IOrderPayService;
import com.mee.manage.service.IOrderService;
import com.mee.manage.service.IPayDetailService;
import com.mee.manage.service.IPayService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.OrderPayVo;
import com.mee.manage.vo.PayMethodVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements IPayService {

    @Autowired
    IOrderService orderService;

    @Autowired
    IOrderPayService orderPayService;

    @Autowired
    IPayDetailService payDetailService;

    @Override
    public List<PayMethodVo> getMethod(Long bizId) {
        PayMethodVo payMethodVo = new PayMethodVo();
        payMethodVo.setMethodCode(PayMethodEnum.BANDK_TRANSDER.getCode());
        payMethodVo.setMethodName(PayMethodEnum.BANDK_TRANSDER.getName());

        List<PayMethodVo> payMethodList = Lists.newArrayList(payMethodVo);
        return payMethodList;
    }

    @Override
    public OrderPayVo getOrderPayInfo(Long orderId) throws MeeException {

        Order order = orderService.getById(orderId);
        if(order == null) {
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);
        }

        List<OrderPay> orderPays = orderPayService.getOrderPay(orderId);

        List<PayDetail> payDetails = payDetailService.getPayDetails(orderId);
        
        if(orderPays != null) {
            orderPays.stream().forEach(item -> item.setPayCodeName(PayMethodEnum.getFeeType(item.getPayCode()).getName()));
        }

        if(payDetails != null) {
            payDetails.stream().forEach(item -> item.setFeeTypeName(FeeTypeEnum.getFeeType(item.getFeeType()).getName()));
        }

        OrderPayVo orderPayInfo = new OrderPayVo();
        orderPayInfo.setOrderPay(orderPays);
        orderPayInfo.setPayDetails(payDetails);

        orderPayInfo.setPayTime(order.getPayTime());
        return orderPayInfo;
    }

    
    
}