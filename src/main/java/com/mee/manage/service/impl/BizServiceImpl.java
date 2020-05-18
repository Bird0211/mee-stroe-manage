package com.mee.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IBizMapper;
import com.mee.manage.po.Biz;
import com.mee.manage.service.IBizService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * BizServiceImpl
 */
@Service
public class BizServiceImpl extends ServiceImpl<IBizMapper, Biz> implements IBizService {

    @Override
    public Biz getBiz(String code) {
        if (code == null || StringUtils.isEmpty(code)) {
            return null;
        }

        QueryWrapper<Biz> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);

        return getOne(queryWrapper);
    }

    
}