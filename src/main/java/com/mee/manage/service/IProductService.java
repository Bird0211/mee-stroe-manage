package com.mee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.Product;
import com.mee.manage.vo.ProductList;
import com.mee.manage.vo.ProductParam;
import com.mee.manage.vo.ProductQueryParam;
import com.mee.manage.vo.ProductVo;
import com.mee.manage.vo.ProdudctFreightVo;

import java.util.List;

public interface IProductService extends IService<Product> {

    Product addProduct(Product product);

    ProductParam addProduct(ProductParam param);

    Product editProduct(ProductVo product);

    List<Product> getProductBySku(Long sku, Long bizId);

    List<Product> queryProductByName(String name);

    List<Product> queryProductByCategoryId(Long categoryId, Long bizId);

    ProductList queryProductByPage(ProductQueryParam param, Long bizId);

    ProductList queryProductByKey(String key, int pageNo, int pageSize, Long bizId);

    List<ProductParam> queryAllProduct(Long bizId);

    /**
     * 根据订单ID 查询物流信息 [productId-ProductFreight]
     * @param productIds
     * @return
     */
    List<ProdudctFreightVo> queryFreight(List<Long> productIds);

}
