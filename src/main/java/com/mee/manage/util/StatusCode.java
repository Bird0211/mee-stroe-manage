package com.mee.manage.util;

public enum StatusCode {

    SUCCESS(0, "SUCCESS"),
    FAIL(1, "FAIL"),
    SYS_ERROR(118000, "Sys Error"),
    DB_ERROR(118001, "DB Error"),
    USER_NOT_EXIST(118002,"User Not Exist"),
    PARAM_ERROR(118003,"Param error"),
    AUTH_FAIL(118006,"Auth Fail"),

    BIZ_NOT_EXIST(11807,"Biz is not exist"),
    PSW_ERROR(11808,"Password is wrong"),
    ORDER_NOT_EXIST(11809,"Order is not exit"),
    ORDER_STATUS_ERROR(11100,"Order status is error"),
    PAY_PRICE_ERROE(11101,"Pyment amount error"),
    ADDRESS_NOT_EXIST(11102,"Address is not exit"),
    FREIGHT_NOT_EXIST(11103,"Freight is not exit"),
    ADDRESS_ERROR(11104,"Address is not support"),



    ;

    private int code;

    private String codeMsg;

    StatusCode(int code, String codeMsg) {
        this.code = code;
        this.codeMsg = codeMsg;
    }

    public int getCode() {
        return code;
    }

    public String getCodeMsg() {
        return codeMsg;
    }

}
