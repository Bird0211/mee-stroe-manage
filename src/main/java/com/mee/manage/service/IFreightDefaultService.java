package com.mee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.FreightDefault;

public interface IFreightDefaultService extends IService<FreightDefault> {

    boolean setDefaultFreight(Long bizId, Long freightId);

    Long getDefaultFreightId(Long bizId);

    FreightDefault getDefaultFreight(Long bizId);

}