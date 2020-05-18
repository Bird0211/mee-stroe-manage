package com.mee.manage.vo;

import java.util.Date;

import lombok.Data;

@Data
public class OrderQueryVo {
    Date startCreateDate;

    Date endCreateDate;

    Date startPayDate;

    Date endPayDate;

    Integer orderSatus;

    Long userId;

    Long pageSize;

    Long pageIndex;

    Long bizId;



}