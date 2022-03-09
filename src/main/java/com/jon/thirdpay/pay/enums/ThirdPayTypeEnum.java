package com.jon.thirdpay.pay.enums;

/**
 * 第三方支付方式枚举
 * @author testjon 2020-08-05
 */
public enum ThirdPayTypeEnum {
    ALIPAY_APP("alipay_app", "支付宝app"),

    ALIPAY_PC("alipay_pc", "支付宝pc"),

    ALIPAY_WAP("alipay_wap", "支付宝wap"),

    ALIPAY_H5("alipay_h5", "支付宝统一下单(h5)"),

    WXPAY_MP("JSAPI", "微信公众号支付"),

    WXPAY_H5("MWEB", "微信H5支付"),

    WXPAY_NATIVE("NATIVE", "微信Native支付"),

    WXPAY_MINI("JSAPI", "微信小程序支付"),

    WXPAY_APP("APP", "微信APP支付");;

    /**
     * 枚举描述
     */
    public final static String ENUM_DESC = "支付方式 WXPAY_APP 微信APP支付、ALIPAY_APP 支付宝APP支付、WXPAY_MINI 微信JSAPI支付、WXPAY_MP 微信公众号支付、WXPAY_H5 微信H5支付";
    private String code;

    private String desc;

    ThirdPayTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static ThirdPayTypeEnum getByName(String name) {
        for (ThirdPayTypeEnum thirdPayTypeEnum : ThirdPayTypeEnum.values()) {
            if (thirdPayTypeEnum.name().equalsIgnoreCase(name)) {
                return thirdPayTypeEnum;
            }
        }
        return null;
    }
}
