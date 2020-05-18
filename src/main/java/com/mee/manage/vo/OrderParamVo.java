package com.mee.manage.vo;

import java.util.List;

import com.mee.manage.po.Order;
import com.mee.manage.po.OrderDetail;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class OrderParamVo extends Order {

    List<OrderDetail> OrderDetails;

}