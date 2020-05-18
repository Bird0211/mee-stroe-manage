package com.mee.manage.controller;

import com.mee.manage.po.Brand;
import com.mee.manage.service.IBrandService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class BrandController extends BaseController {

    @Autowired
    IBrandService brandService;

    @RequestMapping(value = "/brand/list/{bizId}", method = RequestMethod.GET)
    public ResultVo getBrands(@PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            List<Brand> brands = brandService.getBrands(bizId);
            result.setData(brands);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/brand/add/{bizId}", method = RequestMethod.POST)
    public ResultVo addBrands(@RequestParam("brandname") String name, @PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            Brand brand = brandService.addBrand(name,bizId);
            result.setData(brand);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/brand/edit", method = RequestMethod.POST)
    public ResultVo editBrands(@RequestBody Brand brand) {
        ResultVo result = new ResultVo();
        try {
            Brand editBrand = brandService.editBrand(brand);
            result.setData(editBrand);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

}
