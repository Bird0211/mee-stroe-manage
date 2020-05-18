package com.mee.manage.vo;

import java.util.List;

import lombok.Data;

@Data
public class OrderListVo {

    Long total;

    Long pages;

    Long pageNo;

    Long pageSize;

    List<OrderParamVo> orders;

}