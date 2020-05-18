package com.mee.manage.controller;

import java.util.List;

import com.mee.manage.po.Freight;
import com.mee.manage.po.FreightDetail;
import com.mee.manage.service.IFreightDefaultService;
import com.mee.manage.service.IFreightDetailService;
import com.mee.manage.service.IFreightService;
import com.mee.manage.service.IProductFreightService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.FreightVo;
import com.mee.manage.vo.ResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class FreightController extends BaseController {

    @Autowired
    IFreightService freightService;

    @Autowired
    IFreightDetailService freightDetailService;

    @Autowired
    IFreightDefaultService freightDefaultService;

    @Autowired
    IProductFreightService productFreightService;

    @RequestMapping(value = "/freight/add", method = RequestMethod.POST)
    public ResultVo addFreight(@RequestBody FreightVo freight) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = freightService.addFreight(freight);
            if(flag) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/freight/edit", method = RequestMethod.POST)
    public ResultVo editFreight(@RequestBody FreightVo freight) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = freightService.editFreight(freight);
            if(flag) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/freightdetail/add", method = RequestMethod.POST)
    public ResultVo addFeightDetail(@RequestBody FreightDetail freightDetail) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = freightDetailService.save(freightDetail);
            if(flag) {
                result.setData(freightDetail);
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/freight/query/{freightId}", method = RequestMethod.GET)
    public ResultVo queryFreightIdDetail(@PathVariable("freightId") Long freightId) {
        ResultVo result = new ResultVo();
        try {
            FreightVo freight = freightService.getFreight(freightId);
            result.setData(freight);
            result.setStatusCode(StatusCode.SUCCESS.getCode());

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/freight/product/{productId}", method = RequestMethod.GET)
    public ResultVo queryFreightId(@PathVariable("productId") Long productId) {
        ResultVo result = new ResultVo();
        try {
            Long freightId = productFreightService.getFreightByProduct(productId);
            if(freightId != null) {
                result.setData(freightId);
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }


        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/freight/all/{bizId}", method = RequestMethod.GET)
    public ResultVo queryAllFreight(@PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            List<FreightVo> data = freightService.getAllFreight(bizId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/freight/{bizId}", method = RequestMethod.GET)
    public ResultVo queryFreight(@PathVariable("bizId") Long bizId) {
        ResultVo result = new ResultVo();
        try {
            List<Freight> data = freightService.getFreights(bizId);
            result.setData(data);
            result.setStatusCode(StatusCode.SUCCESS.getCode());

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }

        return result;
    }

    @RequestMapping(value = "/freight/default/{bizId}/{freightId}", method = RequestMethod.PUT)
    public ResultVo setDefault(@PathVariable("bizId") Long bizId, @PathVariable("freightId") Long freightId) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = freightDefaultService.setDefaultFreight(bizId, freightId);
            if(flag) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());

            }

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/freight/default/{bizId}", method = RequestMethod.GET)
    public ResultVo getDefault(@PathVariable("bizId") Long bizId){
        ResultVo result = new ResultVo();
        try {
            Long freightId = freightDefaultService.getDefaultFreightId(bizId);
            if(freightId != null) {
                result.setData(freightId);
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());

            }

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/freight/detail/del/{freightDetailId}", method = RequestMethod.DELETE)
    public ResultVo delFreightDetail(@PathVariable("freightDetailId") Long freightDetailId) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = freightDetailService.removeById(freightDetailId);
            if(flag) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/freight/del/{bizId}/{freightId}", method = RequestMethod.DELETE)
    public ResultVo delFreight(@PathVariable("bizId")Long bizId, @PathVariable("freightId") Long freightId) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = freightService.removeFreight(bizId, freightId);
            if(flag) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());

            }

        } catch (Exception ex) {
            logger.info("addFreight error",ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }


}