package com.mee.manage.controller;

import java.util.List;

import com.mee.manage.po.Specification;
import com.mee.manage.service.ISpecificationService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.ResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * SpecificationController
 */
@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class SpecificationController extends BaseController {

    @Autowired
    ISpecificationService specificationService;

    @RequestMapping(value = "/specification/add", method = RequestMethod.POST)
    public ResultVo addSpec(@RequestBody Specification specification) {
        ResultVo result = new ResultVo();
        try {
            Specification spec = specificationService.addSpec(specification);
            result.setData(spec);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addSpec error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/specification/edit/{productId}", method = RequestMethod.POST)
    public ResultVo editSpec(@PathVariable("productId") Long productId, @RequestBody List<Specification> specifications) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = specificationService.updateSpecs(productId, specifications);
            if (flag) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
        } catch (Exception ex) {
            logger.info("editSpec error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/specification/list/{productId}", method = RequestMethod.GET)
    public ResultVo getSpecByProduct(@PathVariable("productId") Long productId) {
        ResultVo result = new ResultVo();
        try {
            List<Specification> specifications = specificationService.getSpec(productId);
            result.setData(specifications);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("editSpec error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }


    @RequestMapping(value = "/specification/del/{id}", method = RequestMethod.DELETE)
    public ResultVo delSpec(@PathVariable("id") Long id) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = specificationService.removeById(id);
            result.setStatusCode(flag ? StatusCode.SUCCESS.getCode() : StatusCode.FAIL.getCode());
        } catch (Exception ex) {
            logger.info("editSpec error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }
}