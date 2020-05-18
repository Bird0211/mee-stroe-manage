package com.mee.manage.controller;

import com.mee.manage.exception.MeeException;
import com.mee.manage.po.Order;
import com.mee.manage.po.OrderAddress;
import com.mee.manage.po.OrderExpress;
import com.mee.manage.service.IOrderService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.ExpressParam;
import com.mee.manage.vo.FeeVo;
import com.mee.manage.vo.OrderListVo;
import com.mee.manage.vo.OrderParamVo;
import com.mee.manage.vo.OrderPayVo;
import com.mee.manage.vo.OrderQueryVo;
import com.mee.manage.vo.PartialDeliveryVo;
import com.mee.manage.vo.ResultVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/api")
@CrossOrigin
public class OrderController extends BaseController {

    @Autowired
    IOrderService orderService;

    @RequestMapping(value = "/order/submit", method = RequestMethod.POST)
    public ResultVo submitOrder(@RequestBody OrderParamVo param) {
        ResultVo result = new ResultVo();
        try {
            Long orderId = orderService.toSubmitOrder(param);
            if(orderId != null && orderId > 0) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
                result.setData(orderId);
            } else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
            
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/address/add", method = RequestMethod.POST)
    public ResultVo addAddress(@RequestBody OrderAddress orderAddress) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = orderService.address(orderAddress);
            if(flag)
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/address/{orderId}", method = RequestMethod.GET)
    public ResultVo getAddress(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            OrderAddress orderAddress = orderService.getAddress(orderId);
            result.setData(orderAddress);
            result.setStatusCode(StatusCode.SUCCESS.getCode());

        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/pay/{orderId}", method = RequestMethod.POST)
    public ResultVo payOrder(@RequestBody OrderPayVo orderPay,@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = orderService.pay(orderPay,orderId);
            if(flag)
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/pay/confirm/{orderId}", method = RequestMethod.POST)
    public ResultVo payOrder(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = orderService.payConfim(orderId);
            if(flag)
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/delivery/{orderId}", method = RequestMethod.POST)
    public ResultVo delivery(@PathVariable("orderId") Long orderId, @RequestParam("expressParam") ExpressParam expressParam) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = orderService.delivery(orderId,expressParam.getExpressCode(),expressParam.getExpressCompany());
            if(flag)
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/partialDelivery", method = RequestMethod.POST)
    public ResultVo partialDelivery(@RequestBody PartialDeliveryVo partialDelivery) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = orderService.partialDelivery (
                    partialDelivery.getExpress(), partialDelivery.getExpressDetails());
            if(flag)
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }
    
    @RequestMapping(value = "/order/complate/{orderId}", method = RequestMethod.POST)
    public ResultVo complate(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = orderService.complate (orderId);
            if(flag)
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/cancel/{orderId}", method = RequestMethod.POST)
    public ResultVo cancel(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = orderService.cancel (orderId);
            if(flag)
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            else {
                result.setStatusCode(StatusCode.FAIL.getCode());
            }
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/queryOrder", method = RequestMethod.POST)
    public ResultVo queryOrder(@RequestBody OrderQueryVo orderQuery) {
        ResultVo result = new ResultVo();
        try {
            OrderListVo orders = orderService.queryOrder(orderQuery);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
            result.setData(orders);
            
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            logger.error("Search Order Error", ex);
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/query/{orderId}", method = RequestMethod.GET)
    public ResultVo getOrder(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            Order order = orderService.getById(orderId);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
            result.setData(order);
            
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/detail/{orderId}", method = RequestMethod.GET)
    public ResultVo getOrderDetail(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            OrderParamVo order = orderService.getOrderDetail(orderId);
            result.setStatusCode(StatusCode.SUCCESS.getCode());
            result.setData(order);
            
        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/editExpress", method = RequestMethod.POST)
    public ResultVo editExpress(@RequestBody OrderExpress orderExpress) {
        ResultVo result = new ResultVo();
        try {
            boolean flag = orderService.editExpress(orderExpress);
            if(flag)
                result.setStatusCode(StatusCode.SUCCESS.getCode());
            else 
                result.setStatusCode(StatusCode.FAIL.getCode());

        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

    @RequestMapping(value = "/order/fee/{orderId}", method = RequestMethod.GET)
    public ResultVo getExpressFree(@PathVariable("orderId") Long orderId) {
        ResultVo result = new ResultVo();
        try {
            FeeVo fee = orderService.getOrderFee(orderId); 
            if(fee != null) {
                result.setStatusCode(StatusCode.SUCCESS.getCode());
                result.setData(fee);
            }
            else 
                result.setStatusCode(StatusCode.FAIL.getCode());

        } catch (MeeException ex) {
            result.setStatusCode(ex.getStatusCode().getCode());
        } catch (Exception ex) {
            result.setStatusCode(StatusCode.FAIL.getCode());
        }
        return result;
    }

}