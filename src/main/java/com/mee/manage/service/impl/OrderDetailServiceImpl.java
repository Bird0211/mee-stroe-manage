package com.mee.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IOrderDetailMapper;
import com.mee.manage.po.OrderDetail;
import com.mee.manage.po.Specification;
import com.mee.manage.service.IOrderDetailService;
import com.mee.manage.service.ISpecificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<IOrderDetailMapper, OrderDetail>
        implements IOrderDetailService {

    @Autowired
    ISpecificationService specificationService;

    @Override
    public boolean updateOrderDetail(List<OrderDetail> orderDetails, Long orderId) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        boolean flag = remove(queryWrapper);
        if(flag) {
            flag = saveBatch(orderDetails);
        }
        return flag;
    }

    @Override
    public List<OrderDetail> getOrderDetails(Long orderId) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        List<OrderDetail> result = list(queryWrapper);

        setOrderSpecInfo(result);

        return result;
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderIds(List<Long> orderIds) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("order_id", orderIds);
        List<OrderDetail> result = list(queryWrapper);

        setOrderSpecInfo(result);

        return result;
    }

    private void setOrderSpecInfo(List<OrderDetail> orderDetails) {
        if (orderDetails == null || orderDetails.isEmpty())
            return;

        List<Specification> specifications = specificationService
                .getSpecByIds(orderDetails.stream().map(item -> item.getSpecId()).collect(Collectors.toList()));

        if (specifications == null || specifications.isEmpty())
            return;

        orderDetails.stream().forEach(item -> {
            List<Specification> specs = specifications.stream()
                    .filter(spec -> spec.getId().compareTo(item.getSpecId()) == 0).collect(Collectors.toList());
            if (specs != null && specs.size() > 0) {
                Specification spec = specs.get(0);
                item.setBarcode(spec.getBarcode());
                item.setImage(spec.getImage());
            }
        });
    }

    @Override
    public List<OrderDetail> getOrderDetailsByIds(List<Long> ids) {
        QueryWrapper<OrderDetail> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        List<OrderDetail> result = list(queryWrapper);

        setOrderSpecInfo(result);

        return result;
    }

    

}