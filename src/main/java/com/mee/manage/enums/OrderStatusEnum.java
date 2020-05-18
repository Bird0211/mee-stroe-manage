package com.mee.manage.enums;


/**
 * OrderStatusEnum
 * 订单状态： 创建订单、填写收款信息、待付款、待发货、已发货、已完成、已取消
 */
public enum OrderStatusEnum {

    CREATE(1001),       //保存订单
    NO_ADDRESS(1002),   //下单完成，待填写地址
    NO_PAYMENT(1003),   //待支付
    NO_DELIVERY(2001),  //已支付,待发货
    PARTIAL_DELIVERY(2002),     //已支付,部分发货
    NO_CONFIRM(2003),   // 已支付，待确认收款
    DELIVERY(3001),     //已发货
    CANCEL(4001),       //已取消
    COMPLETE(0000);     //已完成

    int code;

    OrderStatusEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}