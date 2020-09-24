package com.jon.thirdpay.enums;

import static com.jon.thirdpay.enums.ThirdPayPlatformEnum.ALIPAY;
import static com.jon.thirdpay.enums.ThirdPayPlatformEnum.WX;

/**
 * 第三方支付方式枚举
 * @author testjon 2020-08-05
 */
public enum ThirdPayTypeEnum {
    ALIPAY_APP("alipay_app", ALIPAY, "支付宝app"),

    ALIPAY_PC("alipay_pc", ALIPAY, "支付宝pc"),

    ALIPAY_WAP("alipay_wap", ALIPAY, "支付宝wap"),

    ALIPAY_H5("alipay_h5", ALIPAY, "支付宝统一下单(h5)"),

    WXPAY_MP("JSAPI", WX, "微信公众账号支付"),

    WXPAY_MWEB("MWEB", WX, "微信H5支付"),

    WXPAY_NATIVE("NATIVE", WX, "微信Native支付"),

    WXPAY_MINI("JSAPI", WX, "微信小程序支付"),

    WXPAY_APP("APP", WX, "微信APP支付");
    ;

    /**
     * 枚举描述
     */
    public final static String ENUM_DESC = "支付方式 WXPAY_APP 微信APP支付、ALIPAY_APP 支付宝APP支付";
    private String code;

    private ThirdPayPlatformEnum platform;

    private String desc;

    ThirdPayTypeEnum(String code, ThirdPayPlatformEnum platform, String desc) {
        this.code = code;
        this.platform = platform;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public ThirdPayPlatformEnum getPlatform() {
        return platform;
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
