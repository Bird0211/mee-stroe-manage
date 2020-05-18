package com.mee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.Biz;

/**
 * IBizService
 */
public interface IBizService extends IService<Biz> {

    Biz getBiz(String code);

    
}