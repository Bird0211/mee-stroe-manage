package com.mee.manage.vo;

import java.util.List;

import com.mee.manage.po.OrderDetail;

import lombok.Data;

@Data
public class FreightOrderVo {
    FreightVo freightVo;
    List<OrderDetail> oList;
}