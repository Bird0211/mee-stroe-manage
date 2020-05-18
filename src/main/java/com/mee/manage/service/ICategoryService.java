package com.mee.manage.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.po.Category;
import com.mee.manage.vo.SubCategoryVo;

import java.util.List;

public interface ICategoryService extends IService<Category> {


    Category addCategory(String name,Long pid,Long bizId);

    boolean editCategory(String name,Long id,Long bizId);

    boolean moveCategory(Long id,Long pid,Long bizId);

    List<Category> queryCategoryByPid(Long pid,Long bizId);

    Category getCategoryById(Long id, Long bizId);

    SubCategoryVo getSameSubCateGory(Long categoryId);

}
