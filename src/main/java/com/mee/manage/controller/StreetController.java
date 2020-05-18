package com.mee.manage.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mee.manage.po.City;
import com.mee.manage.service.ICityService;
import com.mee.manage.service.IStreetService;
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
public class StreetController extends BaseController {

    @Autowired
    IStreetService streetService;

    @Autowired
    ICityService cityService;

    @RequestMapping(value = "/street/{name}", method = RequestMethod.GET)
    public ResultVo querySteet(@PathVariable("name") String name) {
        ResultVo result = new ResultVo();
        try {
            List<String> steets = streetService.getStreets(name);
            result.setData(steets);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/city", method = RequestMethod.GET)
    public ResultVo queryCity() {
        ResultVo result = new ResultVo();
        try {
            List<City> citys = cityService.list().stream().sorted(Comparator.comparing(City::getSort)).collect(Collectors.toList());
            result.setData(citys);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/city/search/{text}", method = RequestMethod.GET)
    public ResultVo searchCity(@PathVariable("text") String text) {
        ResultVo result = new ResultVo();
        try {
            List<String> citys = cityService.searchCity(text,8);
            result.setData(citys);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/suburb/search/{city}/{text}", method = RequestMethod.GET)
    public ResultVo searchSuburb(@PathVariable("city") String city,@PathVariable("text") String text) {
        ResultVo result = new ResultVo();
        try {
            List<String> subList = cityService.searchSuburb(city, text);
            result.setData(subList);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
        } catch (Exception ex) {
            logger.info("addProduct error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

}