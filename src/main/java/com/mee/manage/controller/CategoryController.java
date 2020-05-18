package com.mee.manage.controller;

import com.mee.manage.po.Category;
import com.mee.manage.service.ICategoryService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.ResultVo;
import com.mee.manage.vo.SubCategoryVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class CategoryController extends BaseController {

    @Autowired
    ICategoryService categoryService;

    @RequestMapping(value = "/category/add/{bizId}", method = RequestMethod.POST)
    public ResultVo addCategory(@RequestParam("categoryName") String categoryName,
                                @RequestParam("categoryId") Long categoryId,
                                @PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            Category category = categoryService.addCategory(categoryName,categoryId,bizId);
            result.setData(category);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/category/list/{bizId}", method = RequestMethod.POST)
    public ResultVo getCategory(@RequestParam("categoryId") Long categoryId,@PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            List<Category> categories = categoryService.queryCategoryByPid(categoryId,bizId);
            result.setData(categories);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/category/{categoryId}/{bizId}", method = RequestMethod.GET)
    public ResultVo getCategoryById(@PathVariable("categoryId") Long categoryId,@PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            Category categories = categoryService.getCategoryById(categoryId,bizId);
            result.setData(categories);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/subcategory/{categoryId}", method = RequestMethod.GET)
    public ResultVo getCategoryById(@PathVariable("categoryId") Long categoryId) {
        ResultVo result = new ResultVo();
        try {
            SubCategoryVo category = categoryService.getSameSubCateGory(categoryId);
            result.setData(category);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }





}
