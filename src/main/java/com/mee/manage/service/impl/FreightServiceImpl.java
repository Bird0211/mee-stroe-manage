package com.mee.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mee.manage.mapper.IFreightMapper;
import com.mee.manage.po.Freight;
import com.mee.manage.po.FreightDefault;
import com.mee.manage.po.FreightDetail;
import com.mee.manage.service.IFreightDefaultService;
import com.mee.manage.service.IFreightDetailService;
import com.mee.manage.service.IFreightService;
import com.mee.manage.vo.FreightVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class FreightServiceImpl extends ServiceImpl<IFreightMapper, Freight> implements IFreightService {

    @Autowired
    IFreightDetailService freightDetailService;

    @Autowired
    IFreightDefaultService freightDefaultService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addFreight(FreightVo freightVo) {
        Freight freight = freightVo.getFreight();
        boolean flag = save(freight);
        if(flag) {
            List<FreightDetail> freightDetail = freightVo.getFreightDetail();
            freightDetail.stream().forEach(item -> item.setFreightId(freight.getId()));
            flag = freightDetailService.saveBatch(freightDetail);
            if(!flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }

        return flag;
    }

    @Override
    public FreightVo getFreight(Long freightId) {
        FreightVo freightVo = null;
        Freight freight = getById(freightId);
        if(freight != null) {
            freightVo = new FreightVo();
            List<FreightDetail> freightDetail = freightDetailService.gFreightDetail(freightId);
            freightVo.setFreight(freight);
            freightVo.setFreightDetail(freightDetail);
        }
        return freightVo;
    }

    @Override
    public List<FreightVo> getAllFreight(Long bizId) {
        List<FreightVo> list = null;
        List<Freight> freights = getFreights(bizId);
        if (freights != null && freights.size() > 0) {
            List<FreightDetail> freightDetails = freightDetailService.getAllFreightDetails(freights.stream().map(item -> item.getId()).collect(Collectors.toList()));
            list = Lists.newArrayList();
            for (Freight item : freights) {
                FreightVo freightVo = new FreightVo();
                freightVo.setFreight(item);
                List<FreightDetail> details = freightDetails.stream()
                        .filter(i -> i.getFreightId().compareTo(item.getId()) == 0).collect(Collectors.toList());
                freightVo.setFreightDetail(details);
                list.add(freightVo);
            }
        }
        return list;
    }

    public List<Freight> getFreights(Long bizId) {
        QueryWrapper<Freight> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("biz_id", bizId);
        return list(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeFreight(Long bizId, Long freightId) {
        boolean flag = removeById(freightId);
        if(flag) {
            flag = freightDetailService.removeFreightDetail(freightId);
            if(!flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            } else {
                FreightDefault defaultFreight = freightDefaultService.getDefaultFreight(bizId);
                if(defaultFreight != null && defaultFreight.getFreightId().compareTo(freightId) == 0) {
                    List<Freight> freights = getFreights(bizId);
                    if(freights != null && freights.size() > 0) {
                        flag = freightDefaultService.setDefaultFreight(bizId, freights.get(0).getId());
                    } else {
                        flag = freightDefaultService.removeById(defaultFreight.getId());
                    }
                    if(!flag)
                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editFreight(FreightVo freightVo) {
        Freight freight = freightVo.getFreight();
        boolean flag = updateById(freight);
        if(flag) {
            flag = freightDetailService.editFreightDetail(freightVo.getFreightDetail());
            if(!flag)
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return flag;
    }

    @Override
    public List<FreightVo> getFreights(List<Long> freightIds) {
        QueryWrapper<Freight> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", freightIds);
        List<Freight> freights = list(queryWrapper);
        if(freights == null || freightIds.isEmpty())
            return null;

        List<FreightDetail> freightDetails = freightDetailService.getAllFreightDetails(freightIds);
        if(freightDetails == null || freightDetails.isEmpty()) 
            return null;
        List<FreightVo> freightVos = Lists.newArrayList();
        for(Freight freight : freights) {
            FreightVo freightVo = new FreightVo();
            freightVo.setFreight(freight);
            List<FreightDetail> details = freightDetails.stream().filter(item -> item.getFreightId().compareTo(freight.getId()) == 0 ).collect(Collectors.toList());
            freightVo.setFreightDetail(details);
            freightVos.add(freightVo);
        }
        return freightVos;
    }

}