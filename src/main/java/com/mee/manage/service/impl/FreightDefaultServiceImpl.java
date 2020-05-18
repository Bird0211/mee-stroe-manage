package com.mee.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IFreightDefaultMapper;
import com.mee.manage.po.FreightDefault;
import com.mee.manage.service.IFreightDefaultService;

import org.springframework.stereotype.Service;

@Service
public class FreightDefaultServiceImpl extends ServiceImpl<IFreightDefaultMapper, FreightDefault> 
                                                implements IFreightDefaultService{

    @Override
    public boolean setDefaultFreight(Long bizId, Long freightId) {

        Long fId = getDefaultFreightId(bizId);
        boolean flag = false;
        if(fId == null) {
            FreightDefault freightDefault = new FreightDefault();
            freightDefault.setBizId(bizId);
            freightDefault.setFreightId(freightId);
            flag = save(freightDefault);
        } else {
            flag = updateDefault(bizId,freightId);
        }

        return flag;
    }

    private boolean updateDefault(Long bizId, Long freightId) {
        UpdateWrapper<FreightDefault> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("freight_id", freightId);
        updateWrapper.eq("biz_id", bizId);
        return update(updateWrapper);
    }

    @Override
    public Long getDefaultFreightId(Long bizId) {
        Long freightId = null;

        FreightDefault freightDefault = getDefaultFreight(bizId);
        if(freightDefault != null) {
            freightId = freightDefault.getFreightId();
        }
        return freightId;
    }

    @Override
    public FreightDefault getDefaultFreight(Long bizId) {
        QueryWrapper<FreightDefault> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("biz_id", bizId);

        FreightDefault freightDefault = getOne(queryWrapper);
        return freightDefault;
    }

}