package com.mee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.Brand;

import java.util.List;

public interface IBrandService extends IService<Brand> {

    Brand addBrand(String brand, Long bizId);

    Brand editBrand(Brand brand);

    List<Brand> getBrands(Long bizId);

}
