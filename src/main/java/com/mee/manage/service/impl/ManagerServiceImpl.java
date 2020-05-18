package com.mee.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IManagerMapper;
import com.mee.manage.po.Manager;
import com.mee.manage.service.IManagerService;

import org.springframework.stereotype.Service;

/**
 * ServiceImpl
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<IManagerMapper,Manager> implements IManagerService {

    @Override
    public Manager getManager(String username, Long bizId) {
        QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        queryWrapper.eq("biz_id", bizId);
        return getOne(queryWrapper);
    }

    
}