package com.mee.manage.controller;

import java.util.List;

import com.mee.manage.po.Product;
import com.mee.manage.service.IProductService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.ProductKeyParam;
import com.mee.manage.vo.ProductList;
import com.mee.manage.vo.ProductParam;
import com.mee.manage.vo.ProductQueryParam;
import com.mee.manage.vo.ProductVo;
import com.mee.manage.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class ProductController extends BaseController {

    @Autowired
    IProductService productService;

    @RequestMapping(value = "/product/add", method = RequestMethod.POST)
    public ResultVo addProduct(@RequestBody ProductParam product) {
        ResultVo result = new ResultVo();
        try {
            ProductParam products = productService.addProduct(product);
            result.setData(products);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/product/edit", method = RequestMethod.POST)
    public ResultVo editProduct(@RequestBody ProductVo product) {
        ResultVo result = new ResultVo();
        try {
            Product products = productService.editProduct(product);
            if(products != null) {
                result.setData(products);
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
            
        } catch (Exception ex) {
            logger.info("edit Product error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/product/list/{bizId}", method = RequestMethod.POST)
    public ResultVo getProduct(@RequestBody ProductQueryParam param, @PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            ProductList data = productService.queryProductByPage(param, bizId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("query Product error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }


    @RequestMapping(value = "/product/search/{bizId}", method = RequestMethod.POST)
    public ResultVo getProductByKey(@RequestBody ProductKeyParam param, @PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            ProductList data = productService.queryProductByKey(param.getKey(),param.getPageNo(),param.getPageRows(),bizId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("query Product error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    /**
     * 
     * @return
     */
    @RequestMapping(value = "/product/all/{bizId}", method = RequestMethod.GET)
    public ResultVo getPriduct(@PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            List<ProductParam> data = productService.queryAllProduct(bizId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("query Product error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

}
