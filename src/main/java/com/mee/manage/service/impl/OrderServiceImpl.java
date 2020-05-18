package com.mee.manage.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.mee.manage.enums.FeeTypeEnum;
import com.mee.manage.enums.OrderStatusEnum;
import com.mee.manage.enums.PayMethodEnum;
import com.mee.manage.exception.MeeException;
import com.mee.manage.mapper.IOrderMapper;
import com.mee.manage.po.Order;
import com.mee.manage.po.OrderAddress;
import com.mee.manage.po.OrderDetail;
import com.mee.manage.po.OrderExpress;
import com.mee.manage.po.OrderExpressDetail;
import com.mee.manage.po.OrderPay;
import com.mee.manage.po.PayDetail;
import com.mee.manage.service.IOrderAddressService;
import com.mee.manage.service.IOrderDetailService;
import com.mee.manage.service.IOrderExpressDetailService;
import com.mee.manage.service.IOrderExpressService;
import com.mee.manage.service.IOrderPayService;
import com.mee.manage.service.IOrderService;
import com.mee.manage.service.IPayDetailService;
import com.mee.manage.service.ISpecificationService;
import com.mee.manage.util.StatusCode;
import com.mee.manage.vo.FeeDetailVo;
import com.mee.manage.vo.FeeVo;
import com.mee.manage.vo.OrderListVo;
import com.mee.manage.vo.OrderParamVo;
import com.mee.manage.vo.OrderPayVo;
import com.mee.manage.vo.OrderQueryVo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

@Service
public class OrderServiceImpl extends ServiceImpl<IOrderMapper, Order> implements IOrderService {

    public static final Logger logger = LoggerFactory.getLogger(IOrderService.class);

    @Autowired
    IOrderDetailService orderDetailService;

    @Autowired
    IOrderAddressService orderAddressService;

    @Autowired
    IOrderPayService orderPayService;

    @Autowired
    IOrderExpressService orderExpressService;

    @Autowired
    IOrderExpressDetailService orderExpressDetailService;

    @Autowired
    ISpecificationService specificationService;

    @Autowired
    IPayDetailService payDetailService;

    /**
     * 创建
     */
    @Override
    public Long submitOrder(OrderParamVo orderParam) throws MeeException {
        // Long orderId = saveOrSubmit(orderParam, false);
        List<OrderDetail> orderDetails = orderParam.getOrderDetails();
        if (orderDetails == null || orderDetails.isEmpty()) {
            throw new MeeException(StatusCode.PARAM_ERROR);
        }
        int status = OrderStatusEnum.NO_ADDRESS.getCode();

        Order order = new Order();
        order.setBizId(orderParam.getBizId());
        order.setCreateTime(new Date());
        order.setRemark(orderParam.getRemark());
        order.setStatus(status);
        order.setTotalPrice(orderParam.getTotalPrice());
        order.setUserId(orderParam.getUserId());

        boolean flag = save(order);
        Long orderId = null;
        if (flag) {
            if (orderDetails != null && !orderDetails.isEmpty() && order.getId() != null) {
                orderDetails.forEach(item -> item.setOrderId(order.getId()));
                flag = orderDetailService.saveBatch(orderDetails);
                orderId = order.getId();
            }
        }

        if (!flag) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return orderId;
    }

    /**
     * 更新
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long saveOrder(OrderParamVo orderParam) throws MeeException {
        Long orderId = orderParam.getId();

        if (orderId == null || orderId <= 0)
            return null;

        List<OrderDetail> orderDetails = orderParam.getOrderDetails();
        if (orderDetails == null || orderDetails.isEmpty())
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);

        int status = orderParam.getStatus();
        if (status != OrderStatusEnum.CREATE.getCode() && status != OrderStatusEnum.NO_ADDRESS.getCode()
                && status != OrderStatusEnum.NO_PAYMENT.getCode())
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);

        Order order = new Order();
        order.setId(orderId);
        // order.setBizId(orderParam.getBizId());
        // order.setCreateTime(new Date());
        order.setRemark(orderParam.getRemark());
        // order.setStatus(status);
        order.setTotalPrice(orderParam.getTotalPrice());
        // order.setUserId(orderParam.getUserId());
        boolean flag = updateById(order);
        if (flag) {
            orderDetails.forEach(item -> item.setOrderId(order.getId()));
            flag = orderDetailService.updateOrderDetail(orderDetails, orderId);
            if (!flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return orderId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long toSubmitOrder(OrderParamVo orderParam) throws MeeException {
        Long orderId = orderParam.getId();
        if (orderId == null || orderId <= 0) {
            orderId = submitOrder(orderParam);
        } else {
            orderId = saveOrder(orderParam);
        }

        return orderId;
    }

    private boolean updateOrderStatus(Long orderId, int status, int originStatus, BigDecimal totalPrice) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", orderId);
        updateWrapper.eq("status", originStatus);
        updateWrapper.set("status", status);
        if (totalPrice != null) {
            updateWrapper.set("total_price", totalPrice);
        }
        if (status == OrderStatusEnum.NO_DELIVERY.getCode() || status == OrderStatusEnum.NO_CONFIRM.getCode()) {
            updateWrapper.set("pay_time", new Date());
        }

        return update(updateWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean address(OrderAddress orderAddress) throws MeeException {
        Long orderId = orderAddress.getOrderId();
        if (orderId == null || orderId <= 0) {
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);
        }

        boolean flag = false;
        Long addressId = orderAddress.getId();
        if (addressId == null || addressId <= 0) {
            // addAddress
            flag = addAddress(orderAddress);
        } else {
            // saveAddress
            flag = saveAddress(orderAddress);
        }

        return flag;
    }

    @Transactional(rollbackFor = Exception.class)
    private boolean addAddress(OrderAddress orderAddress) throws MeeException {
        Order order = getById(orderAddress.getOrderId());
        if (order == null)
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);

        if (order.getStatus() != OrderStatusEnum.NO_ADDRESS.getCode()) {
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);
        }

        boolean flag = updateOrderStatus(orderAddress.getOrderId(), OrderStatusEnum.NO_PAYMENT.getCode(),
                OrderStatusEnum.NO_ADDRESS.getCode(), null);
        if (flag) {
            flag = orderAddressService.save(orderAddress);
            if (!flag) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
        }
        return flag;
    }

    private boolean saveAddress(OrderAddress orderAddress) throws MeeException {
        Order order = getById(orderAddress.getOrderId());
        if (order == null)
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);

        boolean flag = false;
        if (order.getStatus() == OrderStatusEnum.NO_ADDRESS.getCode()
                || order.getStatus() == OrderStatusEnum.NO_PAYMENT.getCode()
                || order.getStatus() == OrderStatusEnum.NO_DELIVERY.getCode()) {
            flag = orderAddressService.updateById(orderAddress);
        } else {
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);
        }

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean pay(OrderPayVo payVo,Long orderId) throws MeeException {
        List<OrderPay> orderPay = payVo.getOrderPay();
        List<PayDetail> payDetails =payVo.getPayDetails();

        Order order = getById(orderId);
        if(order == null) {
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);
        }

        if(order.getStatus().compareTo(OrderStatusEnum.NO_PAYMENT.getCode()) != 0) {
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);
        }

        BigDecimal totalPay = BigDecimal.ZERO;
        for(OrderPay oPay : orderPay) {
            totalPay = totalPay.add(oPay.getPayPrice());
        }

        BigDecimal orderTotal = BigDecimal.ZERO;
        for(PayDetail pDetail : payDetails) {
            FeeTypeEnum feeType = FeeTypeEnum.getFeeType(pDetail.getFeeType());
            if(feeType == null) {
                throw new MeeException(StatusCode.PAY_PRICE_ERROE);
            }
            if(feeType.getType() == 0) {
                orderTotal = orderTotal.add(pDetail.getPrice());
            } else if(feeType.getType() == 1) {
                orderTotal = orderTotal.subtract(pDetail.getPrice());
            }
        }

        if(totalPay.compareTo(orderTotal) != 0) {
            throw new MeeException(StatusCode.PAY_PRICE_ERROE);
        }

        int orderStatus = 0;
        if(orderPay.stream().filter(item -> item.getPayCode() == PayMethodEnum.BANDK_TRANSDER.getCode()).count() > 0) {
            orderStatus = OrderStatusEnum.NO_CONFIRM.getCode();
        } else {
            orderStatus = OrderStatusEnum.NO_DELIVERY.getCode();
        }
        
        boolean flag = updateOrderStatus(orderId, orderStatus, order.getStatus(),null);
        if(flag) {
            flag = orderPayService.saveBatch(orderPay);
            if(flag) {
                flag = payDetailService.addPayDetails(payDetails);
            }
        }

        if(!flag)
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delivery(Long orderId, String expressCode, String expressCompany) throws MeeException {
        Order order = getById(orderId);
        if (order == null)
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);

        if (order.getStatus() != OrderStatusEnum.NO_DELIVERY.getCode()) {
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);
        }

        boolean flag = updateOrderStatus(orderId, OrderStatusEnum.DELIVERY.getCode(), order.getStatus(), null);
        if (flag) {
            flag = orderExpressService.save(orderId, expressCode, expressCompany);
            if (!flag)
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean partialDelivery(OrderExpress express, List<OrderExpressDetail> details) throws MeeException {
        Long orderId = express.getOrderId();
        Order order = getById(orderId);
        if (order == null)
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);

        if (order.getStatus() != OrderStatusEnum.NO_DELIVERY.getCode()
                && order.getStatus() != OrderStatusEnum.PARTIAL_DELIVERY.getCode())
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);

        List<OrderExpress> orderExpresses = orderExpressService.getOrderExpressByOrderId(orderId);
        int orderStatus = OrderStatusEnum.PARTIAL_DELIVERY.getCode(); // 当前状态
        if (orderExpresses != null && !orderExpresses.isEmpty()) {
            List<OrderDetail> orderDetails = orderDetailService.getOrderDetails(orderId);
            List<OrderExpressDetail> expressDetails = orderExpressDetailService
                    .getExpressDetails(orderExpresses.stream().map(item -> item.getId()).collect(Collectors.toList()));
            if(expressDetails == null) {
                expressDetails = Lists.newArrayList();
            }
            expressDetails.addAll(details);
                    
            boolean isAllExpress = isAllExpress(orderDetails, expressDetails);
            if (isAllExpress) {
                orderStatus = OrderStatusEnum.DELIVERY.getCode();
            }
        }

        if (order.getStatus() != orderStatus) {
            boolean flag = updateOrderStatus(orderId, orderStatus, order.getStatus(), null);
            if (!flag)
                return false;
        }

        boolean expressFlag = orderExpressService.save(express);
        if (expressFlag) {
            details.stream().forEach(item -> item.setExpressId(express.getId()));
            expressFlag = orderExpressDetailService.saveBatch(details);
        }

        if (!expressFlag)
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);

        return expressFlag;
    }

    private boolean isAllExpress(List<OrderDetail> orderDetails, List<OrderExpressDetail> expressDetails) {
        boolean flag = true;
        for (OrderDetail orderDetail : orderDetails) {
            Long orderDetailId = orderDetail.getId();
            Integer number = 0;
            for (OrderExpressDetail expressDetail : expressDetails) {
                if (expressDetail.getOrderDetail().compareTo(orderDetailId) == 0) {
                    number += expressDetail.getNumber();
                }
            }
            if (number.compareTo(orderDetail.getNumber()) != 0) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    @Override
    public boolean complate(Long orderId) {
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", orderId);
        updateWrapper.eq("status", OrderStatusEnum.DELIVERY.getCode());
        updateWrapper.set("status", OrderStatusEnum.COMPLETE.getCode());

        return update(updateWrapper);
    }

    @Override
    public boolean cancel(Long orderId) throws MeeException {
        Order order = getById(orderId);
        if (order.getStatus() == OrderStatusEnum.COMPLETE.getCode()
                || order.getStatus() == OrderStatusEnum.CANCEL.getCode()
                || order.getStatus() == OrderStatusEnum.DELIVERY.getCode()
                || order.getStatus() == OrderStatusEnum.PARTIAL_DELIVERY.getCode())
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);

        return updateOrderStatus(orderId, OrderStatusEnum.CANCEL.getCode(), order.getStatus(), null);
    }

    @Override
    public OrderListVo queryOrder(OrderQueryVo orderQuery) {
        // 分页查询
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if (orderQuery.getUserId() != null) {
            queryWrapper.eq("user_id", orderQuery.getUserId());
        }

        if (orderQuery.getBizId() != null) {
            queryWrapper.eq("biz_id", orderQuery.getBizId());
        }

        if (orderQuery.getStartCreateDate() != null && orderQuery.getEndCreateDate() != null) {
            queryWrapper.between("create_time", orderQuery.getStartCreateDate(), orderQuery.getEndCreateDate());
        }

        if (orderQuery.getOrderSatus() != null) {
            queryWrapper.eq("status", orderQuery.getOrderSatus());
        }

        if (orderQuery.getStartPayDate() != null && orderQuery.getEndPayDate() != null) {
            queryWrapper.between("pay_time", orderQuery.getStartPayDate(), orderQuery.getEndPayDate());
        }

        OrderListVo orderListVo = new OrderListVo();

        Page<Order> ipage = new Page<>(orderQuery.getPageIndex(), orderQuery.getPageSize());
        IPage<Order> pageResult = page(ipage, queryWrapper);
        if (pageResult != null && pageResult.getRecords() != null) {

            List<Order> orders = pageResult.getRecords();

            orderListVo.setPageNo(pageResult.getCurrent());
            orderListVo.setPageSize(pageResult.getSize());
            orderListVo.setPages(pageResult.getPages());
            orderListVo.setTotal(pageResult.getTotal());
            orderListVo.setOrders(getOrderParams(orders));
        } else {
            orderListVo.setPageNo(orderQuery.getPageIndex());
            orderListVo.setPageSize(orderQuery.getPageSize());
            orderListVo.setPages(1L);
            orderListVo.setTotal(0L);
            orderListVo.setOrders(null);
        }
        return orderListVo;
    }

    private List<OrderParamVo> getOrderParams(List<Order> orders) {
        if (orders == null || orders.isEmpty())
            return null;

        List<OrderDetail> orderDetails = orderDetailService
                .getOrderDetailsByOrderIds(orders.stream().map(item -> item.getId()).collect(Collectors.toList()));


        List<OrderParamVo> list = new ArrayList<>();
        for (Order order : orders) {
            OrderParamVo orderParam = new OrderParamVo();
            orderParam.setId(order.getId());
            orderParam.setBizId(order.getBizId());
            orderParam.setCreateTime(order.getCreateTime());
            orderParam.setPayTime(order.getPayTime());
            orderParam.setRemark(order.getRemark());
            orderParam.setStatus(order.getStatus());
            orderParam.setTotalPrice(order.getTotalPrice());
            orderParam.setUserId(order.getUserId());
            orderParam.setOrderDetails(orderDetails.stream()
                    .filter(item -> item.getOrderId().compareTo(order.getId()) == 0).collect(Collectors.toList()));

            list.add(orderParam);
        }

        return list;
    }

    @Override
    public boolean editExpress(OrderExpress orderExpress) {

        return orderExpressService.updateById(orderExpress);
    }

    @Override
    public OrderParamVo getOrderDetail(Long orderId) throws MeeException {
        Order order = getById(orderId);
        OrderParamVo orderParam = null;
        if (order != null) {
            orderParam = new OrderParamVo();
            List<OrderDetail> details = orderDetailService.getOrderDetails(orderId);

            orderParam.setId(order.getId());
            orderParam.setBizId(order.getBizId());
            orderParam.setCreateTime(order.getCreateTime());
            orderParam.setPayTime(order.getPayTime());
            orderParam.setRemark(order.getRemark());
            orderParam.setStatus(order.getStatus());
            orderParam.setTotalPrice(order.getTotalPrice());
            orderParam.setUserId(order.getUserId());

            orderParam.setOrderDetails(details);
        }
        return orderParam;
    }

    @Override
    public OrderAddress getAddress(Long orderId) throws MeeException {

        return orderAddressService.getOrderAddress(orderId);
    }

    @Override
    public FeeVo getOrderFee(Long orderId) throws MeeException {
        Order order = getById(orderId);
        if(order == null)
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);
        
        List<FeeDetailVo> feeDetailVos = Lists.newArrayList();
        BigDecimal totalFee = BigDecimal.ZERO;
        FeeDetailVo feeDetail = new FeeDetailVo();
        feeDetail.setFeeType(FeeTypeEnum.ORDER.getCode());
        feeDetail.setFee(order.getTotalPrice());
        feeDetailVos.add(feeDetail);
        totalFee = totalFee.add(feeDetail.getFee());

        FeeDetailVo addressFee = orderAddressService.getAddressFee(orderId, order.getBizId());
        if(addressFee != null) {
            feeDetailVos.add(addressFee);
            totalFee = totalFee.add(addressFee.getFee());
        }

        FeeVo feeVo = new FeeVo();
        feeVo.setTotalFee(totalFee);
        feeVo.setFeeDetail(feeDetailVos);
        return feeVo;
    }

    @Override
    public boolean payConfim(Long orderId) throws MeeException {
        Order order = getById(orderId);
        if(order == null)
            throw new MeeException(StatusCode.ORDER_NOT_EXIST);
        if(order.getStatus() != OrderStatusEnum.NO_CONFIRM.getCode()) {
            throw new MeeException(StatusCode.ORDER_STATUS_ERROR);
        }

        boolean flag = updateOrderStatus(orderId, OrderStatusEnum.NO_DELIVERY.getCode(), OrderStatusEnum.NO_CONFIRM.getCode(), null);

        return flag;
    }

    
}