package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.PayDetail;

public interface IPayDetailService extends IService<PayDetail> {
    
    List<PayDetail> getPayDetails(Long orderId);

    boolean addPayDetails(List<PayDetail> payDetails);
}