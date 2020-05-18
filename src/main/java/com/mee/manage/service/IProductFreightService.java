package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.ProductFreight;

public interface IProductFreightService extends IService<ProductFreight> {

    Long getFreightByProduct(Long productId);

    boolean editFright(Long productId,Long freightId);

    List<Long> getFreights(List<Long> productIds);

    List<ProductFreight> freights(List<Long> productIds);

}