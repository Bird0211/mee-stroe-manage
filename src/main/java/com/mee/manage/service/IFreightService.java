package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.Freight;
import com.mee.manage.vo.FreightVo;

public interface IFreightService extends IService<Freight> {

    boolean addFreight(FreightVo freightVo);

    boolean editFreight(FreightVo freightVo);

    FreightVo getFreight(Long freightId);

    List<FreightVo> getFreights(List<Long> freightIds);

    List<FreightVo> getAllFreight(Long bizId);

    List<Freight> getFreights(Long bizId);

    boolean removeFreight(Long bizId, Long freightId);

}