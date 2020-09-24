package com.jon.thirdpay.enums;

/**
 * 支付平台枚举
 *
 * @author testjon 2020-08-05
 * @see 暂时没用到
 */
@Deprecated
public enum ThirdPayPlatformEnum {

    ALIPAY("alipay", "支付宝"),

    WX("wx", "微信"),
    ;
    /**
     * 枚举描述
     */
    public final static String ENUM_DESC = "支付平台枚举  alipay 支付宝、wx 微信";

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
