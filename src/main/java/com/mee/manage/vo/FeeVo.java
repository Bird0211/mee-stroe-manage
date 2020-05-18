package com.mee.manage.vo;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class FeeVo {
    BigDecimal totalFee;

    List<FeeDetailVo> feeDetail;

}