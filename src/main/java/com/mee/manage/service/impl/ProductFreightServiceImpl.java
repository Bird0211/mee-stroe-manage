package com.mee.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IProductFreightMapper;
import com.mee.manage.po.ProductFreight;
import com.mee.manage.service.IProductFreightService;
import org.springframework.stereotype.Service;

@Service
public class ProductFreightServiceImpl extends ServiceImpl<IProductFreightMapper, ProductFreight>
        implements IProductFreightService {

    @Override
    public Long getFreightByProduct(Long productId) {
        Long freightId = 0L;
        ProductFreight productFreight = getProductFreight(productId);
        if (productFreight != null)
            freightId = productFreight.getFreightId();
        return freightId;
    }

    private ProductFreight getProductFreight(Long productId) {
        QueryWrapper<ProductFreight> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);
        ProductFreight productFreight = getOne(queryWrapper);
        return productFreight;
    }

    @Override
    public boolean editFright(Long productId, Long freightId) {
        ProductFreight productFreight = getProductFreight(productId);
        boolean flag = true;
        if (freightId == 0) {
            if (productFreight != null) {
                flag = removeById(productFreight.getId());
            }
        } else {
            if (productFreight == null) {
                productFreight = new ProductFreight();
                productFreight.setFreightId(freightId);
                productFreight.setProductId(productId);
                flag = save(productFreight);
            } else {
                if (productFreight.getFreightId().compareTo(freightId) != 0) {
                    productFreight.setFreightId(freightId);
                    flag = updateById(productFreight);
                }
            }
        }
        return flag;
    }

    @Override
    public List<Long> getFreights(List<Long> productIds) {
        List<ProductFreight> freights = freights(productIds);
        List<Long> freightIds = null;
        if(freights != null) {
            freightIds = freights.stream().map(item -> item.getFreightId()).collect(Collectors.toList());
        }
        return freightIds;
    }

    @Override
    public List<ProductFreight> freights(List<Long> productIds) {
        QueryWrapper<ProductFreight> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIds);

        List<ProductFreight> freights = list(queryWrapper);
        return freights;
    }



}