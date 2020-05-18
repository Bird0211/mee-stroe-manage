package com.mee.manage.enums;

public enum FreightTypeEnum {
    NUMBER(0),       //按件计费
    WEIGHT(1);       //按量计费

    int code;

    FreightTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}