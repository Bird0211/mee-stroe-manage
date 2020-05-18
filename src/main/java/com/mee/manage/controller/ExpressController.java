package com.mee.manage.controller;

import java.util.List;

import com.mee.manage.po.OrderDetail;
import com.mee.manage.po.OrderExpress;
import com.mee.manage.service.IExpressService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.ExpressCompanyVo;
import com.mee.manage.vo.ResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class ExpressController extends BaseController {

    @Autowired
    IExpressService expressService;


    @RequestMapping(value = "/express/all/{bizId}", method = RequestMethod.GET)
    public ResultVo getAllExpress(@PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            List<ExpressCompanyVo> data = expressService.getExpressCompany(bizId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/express/{orderId}", method = RequestMethod.GET)
    public ResultVo getExpress(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            List<OrderExpress> data = expressService.getExpress(orderId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/express/detail/{expressId}", method = RequestMethod.GET)
    public ResultVo getExpressDetail(@PathVariable("expressId") Long expressId) {
        ResultVo result = new ResultVo();
        try {
            List<OrderDetail> data = expressService.getExpressDetail(expressId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/express/undilivery/{orderId}", method = RequestMethod.GET)
    public ResultVo getUnDelivery(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            List<OrderDetail> data = expressService.getUnDelivery(orderId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

}