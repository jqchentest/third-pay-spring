package com.jon.thirdpay.enums;

/**
 * 支付平台
 * Created by null on 2019/9/19.
 */
public enum ThirdPayPlatformEnum {

    ALIPAY("alipay", "支付宝"),

    WX("wx", "微信"),
    ;

    private String code;

    private String name;

    ThirdPayPlatformEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
