package com.mee.manage.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mee.manage.exception.MeeException;
import com.mee.manage.po.Order;
import com.mee.manage.po.OrderAddress;
import com.mee.manage.po.OrderExpress;
import com.mee.manage.po.OrderExpressDetail;
import com.mee.manage.vo.FeeVo;
import com.mee.manage.vo.OrderListVo;
import com.mee.manage.vo.OrderParamVo;
import com.mee.manage.vo.OrderPayVo;
import com.mee.manage.vo.OrderQueryVo;

public interface IOrderService extends IService<Order> {
    /**
     * 创建订单
     * @param orderParam
     * @return
     */
    public Long submitOrder(OrderParamVo orderParam) throws MeeException;

    /**
     * 保存订单
     * 没有创建、有更新
     * 已支付完成不能修改状态
     * @param orderParam
     * @return
     */
    public Long saveOrder(OrderParamVo orderParam) throws MeeException;
    
    /**
     * 提交订单
     * 订单状态- 下单->填写地址
     * @return
     */
    public Long toSubmitOrder(OrderParamVo orderParam) throws MeeException;

    /**
     * 待支付订单 填写地址信息
     * @return
     */
    public boolean address(OrderAddress orderAddress) throws MeeException;

    /**
     * 获取地址信息
     * @param orderId
     * @return
     * @throws MeeException
     */
    public OrderAddress getAddress(Long orderId) throws MeeException;

    /**
     * 支付完成，待发货
     * 
     * @return
     */
    public boolean pay(OrderPayVo orderPay,Long orderId) throws MeeException;

    /**
     * 确认支付
     * @param orderId
     * @return
     * @throws MeeException
     */
    public boolean payConfim(Long orderId) throws MeeException;

    /**
     * 已发货
     * @return
     */
    public boolean delivery(Long orderId, String expressCode, String expressCompany) throws MeeException;

    /**
     * 部分发货
     * @param express
     * @param details
     * @return
     */
    public boolean partialDelivery(OrderExpress express, List<OrderExpressDetail> details) throws MeeException;

    /**
     * 已完成
     * @param orderId
     * @return
     */
    public boolean complate(Long orderId) throws MeeException;

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    public boolean cancel(Long orderId) throws MeeException;

    /**
     * 查询订单：创建时间，订单状态，用户id
     * @return
     */
    public OrderListVo queryOrder(OrderQueryVo orderQuery) throws MeeException;

    /**
     * 修改快递信息
     * @param orderExpress
     * @return
     */
    public boolean editExpress(OrderExpress orderExpress) throws MeeException;

    /**
     * 查询订单详情
     * @param orderId
     * @return
     * @throws MeeException
     */
    public OrderParamVo getOrderDetail(Long orderId) throws MeeException;

    /**
     * 查询需要支付金额
     * @param orderId
     * @return
     * @throws MeeException
     */
    public FeeVo getOrderFee(Long orderId) throws MeeException;
}