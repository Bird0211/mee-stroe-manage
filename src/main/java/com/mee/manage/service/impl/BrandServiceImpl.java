package com.mee.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.IBrandMapper;
import com.mee.manage.po.Brand;
import com.mee.manage.service.IBrandService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl extends ServiceImpl<IBrandMapper, Brand> implements IBrandService {

    @Override
    public Brand addBrand(String brandName, Long bizId) {
        Brand brand = new Brand();
        brand.setBrandName(brandName);
        brand.setBizId(bizId);
        boolean result = save(brand);
        if(result)
            return brand;
        else
            return null;
    }

    @Override
    public Brand editBrand(Brand brand) {
        boolean result = updateById(brand);
        if(result)
            return brand;
        else
            return null;
    }

    @Override
    public List<Brand> getBrands(Long bizId) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("biz_id", bizId);
        return list(queryWrapper);
    }
}
