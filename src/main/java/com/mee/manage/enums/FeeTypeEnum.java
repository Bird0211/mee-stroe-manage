package com.mee.manage.enums;

/**
 * 费用类型
 */
public enum FeeTypeEnum {
    ORDER(0,0,"订单费用"),       //订单费用
    EXPRESS(1,0,"物流费用");     //物流费用

    int code;
    int type;   //0:加；1：减
    String name;

    FeeTypeEnum(int code,int type,String name) {
        this.code = code;
        this.type = type;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public int getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public static FeeTypeEnum getFeeType(int code) {
        for(FeeTypeEnum feeType: FeeTypeEnum.values())  {
            if(feeType.getCode() == code)
                return feeType;
        }
        return null;
    }

}