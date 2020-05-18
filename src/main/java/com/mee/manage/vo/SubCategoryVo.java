package com.mee.manage.vo;

import java.util.List;

import com.mee.manage.po.Category;

import lombok.Data;

@Data
public class SubCategoryVo {
    Long pid;

    List<Category> category;

}