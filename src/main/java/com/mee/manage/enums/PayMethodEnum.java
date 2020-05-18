package com.mee.manage.enums;

/**
 * 支付方式
 */
public enum PayMethodEnum {
    BANDK_TRANSDER(0,"银行转账"),       //银行转账
    CASH_DELIVERY(1,"到账付款"),        //到账
    Paypal(2,"PayPal"),           //在线支付
    WEICHAT(3,"微信支付"),         
    ALIPAY(4,"支付宝支付");

    int code;
    String name;

    PayMethodEnum(int code,String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static PayMethodEnum getFeeType(int code) {
        for(PayMethodEnum feeType: PayMethodEnum.values())  {
            if(feeType.getCode() == code)
                return feeType;
        }
        return null;
    }

}