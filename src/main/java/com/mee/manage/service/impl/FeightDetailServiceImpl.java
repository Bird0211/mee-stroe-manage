package com.mee.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IFreightDetailMapper;
import com.mee.manage.po.FreightDetail;
import com.mee.manage.service.IFreightDetailService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class FeightDetailServiceImpl extends ServiceImpl<IFreightDetailMapper, FreightDetail>
        implements IFreightDetailService {

    @Override
    public List<FreightDetail> gFreightDetail(Long freightId) {
        QueryWrapper<FreightDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("freight_id", freightId);
        return list(queryWrapper);
    }

    @Override
    public List<FreightDetail> getAllFreightDetails(List<Long> freightIds) {
        QueryWrapper<FreightDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("freight_id", freightIds);
        return list(queryWrapper);
    }

    @Override
    public boolean removeFreightDetail(Long freightId) {
        QueryWrapper<FreightDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("freight_id", freightId);
        return remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public boolean editFreightDetail(List<FreightDetail> freightDetails) {
        List<FreightDetail> addList = getAddList(freightDetails);
        List<FreightDetail> updateList = getUpdateList(freightDetails);



        boolean flag = removeFreightDetail(updateList);
        if(flag) {
            flag = updateFreightDetail(updateList);
            if(flag) {
                flag = addFreightDetail(addList);
                if(!flag) 
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
            } else {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
            }
        }

        return flag;
    }

    private boolean addFreightDetail(List<FreightDetail> freightDetails) {
        if(freightDetails == null || freightDetails.size() <= 0)
            return true;

        return saveBatch(freightDetails);
    }

    private boolean updateFreightDetail(List<FreightDetail> freightDetails) {
        if(freightDetails == null || freightDetails.size() <= 0)
            return true;
        
        return updateBatchById(freightDetails);
    }

    private List<FreightDetail> getAddList(List<FreightDetail> freightDetails) {
        return freightDetails.stream().filter(item -> item.getId() == null || item.getId() <= 0).collect(Collectors.toList());
    }

    private List<FreightDetail> getUpdateList(List<FreightDetail> freightDetails) {
        return freightDetails.stream().filter(item -> item.getId() != null && item.getId() > 0).collect(Collectors.toList());
    }

    private boolean removeFreightDetail(List<FreightDetail> freightDetails) {
        if(freightDetails == null || freightDetails.size() <= 0)
            return true;
        
        List<Long> ids = freightDetails.stream().map(item -> item.getId()).collect(Collectors.toList());
        QueryWrapper<FreightDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("freight_id", freightDetails.get(0).getFreightId());
        queryWrapper.notIn("id", ids);
        remove(queryWrapper);
        return true;
    }


}