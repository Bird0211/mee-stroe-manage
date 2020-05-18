package com.mee.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.mee.manage.enums.ExpressCompanyEnum;
import com.mee.manage.enums.ExpressTypeEnum;
import com.mee.manage.po.OrderDetail;
import com.mee.manage.po.OrderExpress;
import com.mee.manage.po.OrderExpressDetail;
import com.mee.manage.service.IExpressService;
import com.mee.manage.service.IOrderDetailService;
import com.mee.manage.service.IOrderExpressDetailService;
import com.mee.manage.service.IOrderExpressService;
import com.mee.manage.vo.ExpressCompanyVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpressServiceImpl implements IExpressService {

    @Autowired
    IOrderExpressService orderExpressService;

    @Autowired
    IOrderExpressDetailService expressDetailService;

    @Autowired
    IOrderDetailService orderDetailService;

    public static final Logger logger = LoggerFactory.getLogger(IExpressService.class);

    @Override
    public List<ExpressCompanyVo> getExpressCompany(Long bizId) {
        List<ExpressCompanyVo> companyVos = null;
        ExpressCompanyEnum[] allCompanyEnums = ExpressCompanyEnum.values();
        if (allCompanyEnums != null && allCompanyEnums.length > 0) {
            companyVos = Lists.newArrayList();
            for (ExpressCompanyEnum eCompanyEnum : allCompanyEnums) {
                ExpressCompanyVo eCompanyVo = new ExpressCompanyVo();
                eCompanyVo.setCode(eCompanyEnum.getCode());
                eCompanyVo.setName(eCompanyEnum.getName());
                companyVos.add(eCompanyVo);
            }
        }

        return companyVos;
    }

    @Override
    public List<OrderExpress> getExpress(Long orderId) {
        List<OrderExpress> orderExpress = orderExpressService.getOrderExpressByOrderId(orderId);
        return orderExpress;
    }

    @Override
    public List<OrderDetail> getUnDelivery(Long orderId) {
        List<OrderDetail> list = orderDetailService.getOrderDetails(orderId);
        logger.info("OrderDetail = {}",list);
        List<OrderDetail> result = null;
        List<OrderExpress> expresses = getExpress(orderId);
        if(list != null && list.size() > 0 && expresses != null && expresses.size() > 0) {
            List<Long> expressIds = expresses.stream().filter(item -> item.getType() == ExpressTypeEnum.PARTIAL_DELIVERY.getCode()).
                                        map(item -> item.getId()).collect(Collectors.toList());
            if(expressIds != null && expressIds.size() > 0) {
                List<OrderExpressDetail> expressDetails = expressDetailService.getExpressDetails(expressIds);
                if(expressDetails != null && expressDetails.size() > 0) {

                    for(OrderDetail oDetail : list) {
                        Long orderDetailId = oDetail.getId();
                        Integer number = oDetail.getNumber();
                        if(expressDetails.stream().filter(item -> item.getOrderDetail().compareTo(orderDetailId) == 0).count() > 0) {
                            List<Integer> num = expressDetails.stream().filter(item -> item.getOrderDetail().compareTo(orderDetailId) == 0).
                                       map(item -> item.getNumber()).collect(Collectors.toList());
                            
                            if(num != null && num.size() > 0) {
                                for(Integer n: num) {
                                    number -= n;
                                }
                                oDetail.setNumber(number);

                            }
                        }
                    }
                }
            }
        } 

        result = list.stream().filter(item -> item.getNumber() > 0).collect(Collectors.toList());

        return result;
    }

    @Override
    public List<OrderDetail> getExpressDetail(Long expressId) {
        List<OrderDetail> orderDetails = null;
        List<OrderExpressDetail> expressDetails = expressDetailService.getExpressDetail(expressId);
        if(expressDetails != null && expressDetails.size() > 0) {
            List<Long> ids = expressDetails.stream().map(item -> item.getOrderDetail()).collect(Collectors.toList());
            if(ids != null && ids.size() > 0) {
                orderDetails = orderDetailService.getOrderDetailsByIds(ids);
                if(orderDetails != null && orderDetails.size() > 0) {
                   for(OrderDetail oDetail : orderDetails) {
                    List<OrderExpressDetail> eDetails = expressDetails.stream().filter(item -> item.getOrderDetail().compareTo(oDetail.getId()) == 0).collect(Collectors.toList());
                    if(eDetails != null && eDetails.size() > 0) {
                        oDetail.setNumber(eDetails.get(0).getNumber());
                    }
                   } 
                }
            }
        }
        return orderDetails;
    }
    
}