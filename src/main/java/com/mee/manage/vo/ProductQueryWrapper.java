package com.mee.manage.vo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mee.manage.po.Category;
import com.mee.manage.po.Product;
import com.mee.manage.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
@Scope(value = "prototype")
public class ProductQueryWrapper extends QueryWrapper<Product> {

    /**
     *
     */
    private static final long serialVersionUID = 7190027623272994912L;


    @Autowired
    ICategoryService categoryService;

    public QueryWrapper<Product> getQueryWrapperByCategoryId(Long categoryId,Long bizId){
        List<Long> cids = new ArrayList<>();
        List<Category> subCategory = categoryService.queryCategoryByPid(categoryId,bizId);
        if(subCategory != null) {
            for (Category category : subCategory)
                cids.add(category.getId());
        } else {
            cids.add(categoryId);
        }
        this.in("category_id",cids);

        return this;
    }


    public QueryWrapper<Product> getQueryWrapperBySku(Long sku) {
        this.eq("barcode",sku);
        return this;
    }

    public QueryWrapper<Product> getQueryWrapperByName(String name) {
        this.like("name",name.replaceAll(" ","%"));
        return this;
    }

    public QueryWrapper<Product> getQueryWrapperByBrand(Long brandId) {
        this.eq("brand_id",brandId);
        return this;
    }

}
