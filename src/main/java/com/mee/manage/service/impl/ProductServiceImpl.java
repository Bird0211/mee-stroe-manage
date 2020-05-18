package com.mee.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.config.Config;
import com.mee.manage.mapper.IProductsMapper;
import com.mee.manage.po.Product;
import com.mee.manage.po.ProductFreight;
import com.mee.manage.po.Specification;
import com.mee.manage.service.IAliOssService;
import com.mee.manage.service.IProductFreightService;
import com.mee.manage.service.IProductService;
import com.mee.manage.service.ISpecificationService;
import com.mee.manage.vo.ProductList;
import com.mee.manage.vo.ProductParam;
import com.mee.manage.vo.ProductQueryParam;
import com.mee.manage.vo.ProductQueryWrapper;
import com.mee.manage.vo.ProductVo;
import com.mee.manage.vo.ProdudctFreightVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl extends ServiceImpl<IProductsMapper, Product> implements IProductService {

    public static final Logger logger = LoggerFactory.getLogger(IProductService.class);

    @Autowired
    Config config;

    @Autowired
    ISpecificationService specificationService;

    @Autowired
    IAliOssService ossService;

    @Autowired
    IProductFreightService productFreightService;

    @Lookup
    public ProductQueryWrapper getQueryWrapper() {
        return null;
    }

    @Override
    public Product addProduct(Product product) {
        if (product == null)
            return null;

        Product p = save(product) ? product : null;
        if (p != null)
            setShowImg(p);
        return p;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Product editProduct(ProductVo product) {
        if (product == null)
            return null;

        Product p = updateById(product) ? product : null;
        if (p != null) {
            boolean flag = productFreightService.editFright(product.getId(),product.getFreightId());
            if(!flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                p = null;
            }else {
                setShowImg(p);
            }
        }
        return p;
    }

    @Override
    public List<Product> getProductBySku(Long sku, Long bizId) {
        if (sku == null)
            return null;

        List<Specification> specifications = specificationService.gSpecificationBySKU(sku.toString());
        if (specifications == null || specifications.isEmpty())
            return null;

        List<Long> ids = specifications.stream().map(item -> item.getProductId()).collect(Collectors.toList());
        return getByIds(ids, bizId);
    }

    public List<Product> getByIds(List<Long> ids, Long bizId) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        queryWrapper.eq("biz_id", bizId);
        List<Product> products = list(queryWrapper);
        setShowImgs(products);
        return products;
    }

    @Override
    public List<Product> queryProductByName(String name) {
        if (name == null || StringUtils.isEmpty(name)) {
            return null;
        }

        List<Product> products = list(getQueryWrapper().getQueryWrapperByName(name));
        if (products != null && products.size() > 0) {
            products.forEach(item -> setShowImg(item));
        }
        return products;
    }

    @Override
    public List<Product> queryProductByCategoryId(Long categoryId, Long bizId) {
        if (categoryId == null || categoryId <= 0) {
            return null;
        }
        List<Product> products = list(getQueryWrapper().getQueryWrapperByCategoryId(categoryId, bizId));
        setShowImgs(products);
        return products;
    }

    @Override
    public ProductList queryProductByPage(ProductQueryParam param, Long bizId) {
        if (param == null)
            return null;

        ProductList result = new ProductList();
        List<Product> products = new ArrayList<>();
        long total = 0;
        long pages = 1;

        if (param.getSku() != null && param.getSku() > 0) {
            List<Product> skuProducts = getProductBySku(param.getSku(), bizId);
            if (skuProducts != null) {
                products.addAll(skuProducts);
                total = Long.valueOf(products.size());
            }
        }

        if (total == 0) {
            ProductQueryWrapper queryWrapper = getQueryWrapper();
            if (param.getCategoryId() != null && param.getCategoryId() > 0) {
                queryWrapper.getQueryWrapperByCategoryId(param.getCategoryId(), bizId);
            }

            if (!StringUtils.isEmpty(param.getName())) {
                queryWrapper.getQueryWrapperByName(param.getName());
            } else if (param.getSku() != null && param.getSku() > 0) {
                queryWrapper.getQueryWrapperByName(param.getSku().toString());
            }

            if (param.getBrandId() != null && param.getBrandId() > 0) {
                queryWrapper.getQueryWrapperByBrand(param.getBrandId());
            }

            queryWrapper.eq("biz_id", bizId);

            Page<Product> ipage = new Page<>(param.getPageNo(), param.getPageRows());

            IPage<Product> pageResult = page(ipage, queryWrapper);
            if (pageResult != null && pageResult.getRecords() != null) {
                products.addAll(pageResult.getRecords());
                total = pageResult.getTotal();
                pages = pageResult.getPages();

                products.stream().forEach(item -> setShowImg(item));
            }
        }
        result.setProducts(products);
        result.setPageNo(param.getPageNo());
        result.setPageRows(param.getPageRows());
        result.setTotal(total);
        result.setPages(pages);
        return result;
    }

    @Override
    public ProductList queryProductByKey(String key, int pageNo, int pageSize, Long bizId) {
        if (StringUtils.isEmpty(key))
            return ProductList.getEmptyEntry();

        ProductQueryParam param = new ProductQueryParam();
        param.setPageNo(pageNo);
        param.setPageRows(pageSize);

        if (org.apache.commons.lang3.StringUtils.isNumeric(key)) {
            param.setSku(Long.parseLong(key));
        } else {
            param.setName(key);
        }

        ProductList productList = queryProductByPage(param, bizId);
        return productList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductParam addProduct(ProductParam param) {
        Product product = new Product();
        product.setBizId(param.getBizId());
        product.setBrandId(param.getBrandId());
        product.setBrandName(param.getBrandName());
        product.setCategoryId(param.getCategoryId());
        product.setCategoryName(param.getCategoryName());
        product.setDescription(param.getDescription());
        product.setImage(param.getImage());
        product.setName(param.getName());
        product.setStatus(param.getStatus());

        product = addProduct(product);
        if (product == null || product.getId() == null || product.getId() == 0) {
            return null;
        }

        Long productId = product.getId();
        param.setId(productId);

        List<Specification> specifications = param.getSpecifications();
        if (specifications != null) {
            specifications.stream().forEach(item -> item.setProductId(productId));
            if (specifications != null && !specifications.isEmpty()) {
                specifications = specificationService.addSpecs(specifications);
                if (specifications != null && !specifications.isEmpty()) {
                    param.setSpecifications(specifications);
                } else {
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                }
            }
        }

        if (param.getFreightId() != null && param.getFreightId() > 0) {
            ProductFreight productFreight = new ProductFreight();
            productFreight.setFreightId(param.getFreightId());
            productFreight.setProductId(productId);

            boolean flag = productFreightService.save(productFreight);
            if (!flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return param;
    }

    private void setShowImg(Product product) {
        if (product != null && org.apache.commons.lang3.StringUtils.isNotBlank(product.getImage())) {
            String imgUrl = ossService.getImgUrl(product.getImage());
            product.setImage(imgUrl);
        }
    }

    private void setShowImgs(List<Product> products) {
        if (products != null && products.size() > 0) {
            products.forEach(item -> setShowImg(item));
        }
    }

    @Override
    public List<ProductParam> queryAllProduct(Long bizId) {
        List<Product> products = getProdcutByBizId(bizId, 0);
        List<ProductParam> params = null;
        if (products != null) {
            params = new ArrayList<>();
            setShowImgs(products);
            List<Long> productIds = products.stream().map(item -> item.getId()).collect(Collectors.toList());
            List<Specification> specifications = specificationService.getSpecs(productIds);
            logger.info("specifications = {}", specifications);
            for (Product item : products) {
                ProductParam param = new ProductParam();
                param.setBizId(item.getBizId());
                param.setBrandId(item.getBrandId());
                param.setBrandName(item.getBrandName());
                param.setCategoryId(item.getCategoryId());
                param.setCategoryName(item.getBrandName());
                param.setDescription(item.getDescription());
                param.setId(item.getId());
                param.setImage(item.getImage());
                param.setName(item.getName());
                param.setStatus(item.getStatus());

                List<Specification> sList = specifications.stream()
                        .filter(i -> i.getProductId().compareTo(item.getId()) == 0).collect(Collectors.toList());
                logger.info("ProductId = {} , Spec = {}", item.getId(), sList);
                param.setSpecifications(sList);

                params.add(param);
            }
        }
        return params;
    }

    public List<Product> getProdcutByBizId(Long bizId, Integer status) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("biz_id", bizId);
        if (status != null)
            queryWrapper.eq("status", status);
        return list(queryWrapper);
    }

    @Override
    public List<ProdudctFreightVo> queryFreight(List<Long> productIds) {
        
        return null;
    }

}
