package com.mee.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mee.manage.mapper.ICategoryMapper;
import com.mee.manage.po.Category;
import com.mee.manage.service.ICategoryService;
import com.mee.manage.vo.SubCategoryVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServiceImpl<ICategoryMapper, Category> implements ICategoryService {

    public static final Logger logger = LoggerFactory.getLogger(ICategoryService.class);

    @Override
    public Category addCategory(String name, Long pid, Long bizId) {
        if (StringUtils.isEmpty(name) || pid == null)
            return null;

        Category category = new Category();
        category.setName(name);
        category.setPid(pid);
        category.setBizId(bizId);

        boolean flag = save(category);
        if (flag) {
            logger.info("NewCateGoryId = {}", category.getId());
            logger.info("NewCate = {}", category);
            return category;
        } else
            return null;
    }

    @Override
    public boolean editCategory(String name, Long id, Long bizId) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<Category>();
        updateWrapper.set("name", name);
        updateWrapper.eq("id", id);
        updateWrapper.eq("biz_id", bizId);

        return update(updateWrapper);
    }

    @Override
    public boolean moveCategory(Long id, Long pid, Long bizId) {
        UpdateWrapper<Category> updateWrapper = new UpdateWrapper<Category>();
        updateWrapper.set("pid", pid);
        updateWrapper.eq("id", id);
        updateWrapper.eq("biz_id", bizId);

        return update(updateWrapper);
    }

    @Override
    public List<Category> queryCategoryByPid(Long pid, Long bizId) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<Category>();
        queryWrapper.eq("pid", pid);
        queryWrapper.eq("biz_id", bizId);
        return list(queryWrapper);
    }

    @Override
    public Category getCategoryById(Long id, Long bizId) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("biz_id", bizId);
        return getOne(queryWrapper);
    }

    @Override
    public SubCategoryVo getSameSubCateGory(Long categoryId) {
        Category category = getById(categoryId);
        SubCategoryVo subCategoryVo = null;
        if(category != null) {
            subCategoryVo = new SubCategoryVo();
            List<Category> subCategories = queryCategoryByPid(category.getPid(), category.getBizId());
            subCategoryVo.setPid(category.getPid());
            subCategoryVo.setCategory(subCategories);
        }
        return subCategoryVo;
    }


}
