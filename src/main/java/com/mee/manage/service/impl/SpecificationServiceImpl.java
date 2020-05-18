package com.mee.manage.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mee.manage.mapper.ISpecificationMapper;
import com.mee.manage.po.Specification;
import com.mee.manage.service.IAliOssService;
import com.mee.manage.service.ISpecificationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * SpecificationServiceImpl
 */
@Service
public class SpecificationServiceImpl extends ServiceImpl<ISpecificationMapper, Specification>
        implements ISpecificationService {

    @Autowired
    IAliOssService ossService;
    
    @Override
    public List<Specification> addSpecs(List<Specification> specifications) {
        boolean flag = saveBatch(specifications);
        if(flag) {
            setShowImgs(specifications);
            return specifications;
        }
        else
            return null;
    }

    /**
     * 
     * @param productId
     * @param specifications
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public boolean updateSpecs(Long productId, List<Specification> specifications) {
        List<Specification> list = getSpec(productId);

        List<Specification> addList = specifications.stream().filter(item -> item.getId() == null || item.getId() <= 0).collect(Collectors.toList());

        List<Specification> newSpecifications = specifications.stream().filter(item -> item.getId() != null && item.getId() > 0).collect(Collectors.toList());
        List<Specification> delList = getDiffSpecification(newSpecifications,list);

        List<Specification> updateList = getUpdateSpecification(list, newSpecifications);

        boolean flag = false;

        if ( addList != null && addList.size() > 0) {
            flag = saveBatch(addList);
        }

        if (delList != null && delList.size() > 0) {
            List<Long> ids = delList.stream().map(item -> item.getId()).collect(Collectors.toList());
            flag = removeByIds(ids);
        }

        if (updateList != null && updateList.size() > 0) {
            flag = updateBatchById(updateList);
        }

        if(!flag){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly(); 
        }

        return flag;

    }

    private List<Specification> getDiffSpecification(List<Specification> specifications, List<Specification> newSpecifications) {
        if(specifications == null || specifications.isEmpty()) {
            return newSpecifications;
        }

        List<Specification> addSpec = newSpecifications.stream().filter( 
            item -> 
                specifications.stream().filter(
                     i -> i.getId().compareTo(item.getId()) == 0)
                .count() == 0 ).collect(Collectors.toList());

        return addSpec;
    }

    private List<Specification> getUpdateSpecification (List<Specification> specifications, List<Specification> newSpecifications) {
        if(specifications == null || newSpecifications == null)
            return null;

        List<Specification> list = Lists.newArrayList();
        for(Specification newSpec : newSpecifications) {
            for(Specification spec : specifications) {
                if(newSpec.getId().compareTo(spec.getId()) == 0) {
                    if(!newSpec.equals(spec)) {
                        list.add(newSpec);
                    }
                }
            }
        }

        return list;
    }

    @Override
    public Specification addSpec(Specification specification) {
        boolean flag = save(specification);
        if (flag) {
            setShowImg(specification);
            return specification;
        } else
            return null;
        }

    @Override
    public List<Specification> gSpecificationBySKU(String barcode) {
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("barcode", barcode);

        List<Specification> specification = list(queryWrapper);
        setShowImgs(specification);
        return specification;
    }

    @Override
    public List<Specification> getSpec(Long productId) {
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", productId);

        List<Specification> specifications = list(queryWrapper);
        setShowImgs(specifications);
        return specifications;
    }

    private void setShowImg(Specification specification) {
        if(specification != null && org.apache.commons.lang3.StringUtils.isNotBlank(specification.getImage())) {
            String imgUrl = ossService.getImgUrl(specification.getImage());
            specification.setImage(imgUrl);
        }
    }

    private void setShowImgs(List<Specification> specifications) {
        if(specifications == null) 
            return ;

        specifications.stream().forEach(item -> setShowImg(item));
    }

    @Override
    public List<Specification> getSpecs(List<Long> productIds) {
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIds);
        List<Specification> specifications = list(queryWrapper);
        setShowImgs(specifications);

        return specifications;
    }

    @Override
    public List<Specification> getSpecByIds(List<Long> ids) {
        QueryWrapper<Specification> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id", ids);
        
        List<Specification> specifications = list(queryWrapper);
        setShowImgs(specifications);

        return specifications;
    }
    
}