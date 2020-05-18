package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.Specification;

/**
 * ISpecification
 */
public interface ISpecificationService extends IService<Specification> {

    List<Specification> addSpecs(List<Specification> specifications);

    Specification addSpec(Specification specification);

    List<Specification> gSpecificationBySKU(String barcode);

    List<Specification> getSpec(Long productId);

    List<Specification> getSpecs(List<Long> productIds);

    List<Specification> getSpecByIds(List<Long> ids);

    public boolean updateSpecs(Long productId, List<Specification> specifications);
    
}