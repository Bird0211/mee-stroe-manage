package com.mee.manage.vo;

import java.util.List;

import com.mee.manage.po.Freight;
import com.mee.manage.po.FreightDetail;

import lombok.Data;

@Data
public class FreightVo {
    Freight freight;

    List<FreightDetail> freightDetail;
}