package com.mee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.OrderAddress;
import com.mee.manage.vo.FeeDetailVo;

public interface IOrderAddressService extends IService<OrderAddress> {

    OrderAddress getOrderAddress(Long orderId);

    FeeDetailVo getAddressFee(Long orderId,Long bizId);

}