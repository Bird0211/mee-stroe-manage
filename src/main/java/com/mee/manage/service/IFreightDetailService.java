package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.FreightDetail;

public interface IFreightDetailService extends IService<FreightDetail> {

    List<FreightDetail> gFreightDetail(Long freightId);

    List<FreightDetail> getAllFreightDetails(List<Long> freightIds);

    boolean removeFreightDetail(Long freightId);

    boolean editFreightDetail(List<FreightDetail> freightDetails);

}