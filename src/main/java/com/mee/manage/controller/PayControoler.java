package com.mee.manage.controller;

import com.mee.manage.service.IPayService;
import com.mee.manage.util.StatusCode;
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
public class PayControoler extends BaseController {

    @Autowired
    IPayService PayService;

    @RequestMapping(value = "/pay/method/{bizId}", method = RequestMethod.GET)
    public ResultVo getPayMethod(@PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            result.setData(PayService.getMethod(bizId));
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("query Product error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/pay/info/{orderId}", method = RequestMethod.GET)
    public ResultVo getPayInfo(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            result.setData(PayService.getOrderPayInfo(orderId));
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("query Product error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }
}