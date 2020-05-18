package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.OrderDetail;

public interface IOrderDetailService extends IService<OrderDetail> {

    boolean updateOrderDetail(List<OrderDetail> orderDetails,Long orderId);

    List<OrderDetail> getOrderDetails(Long orderId);

    List<OrderDetail> getOrderDetailsByOrderIds(List<Long> orderIds);

    List<OrderDetail> getOrderDetailsByIds(List<Long> ids);

}