package com.jon.thirdpay.pay.enums;

import lombok.Getter;

/**
 * 订单状态枚举
 *
 * @author testjon 2020-08-05
 */
@Getter
public enum ThirdPayOrderStatusEnum {

    SUCCESS("支付成功"),

    REFUND("订单已退款"),

    NOTPAY("订单未支付"),

    CLOSED("订单已关闭"),

    /**
     * 已撤销（刷卡支付）
     */
    REVOKED("已撤销"),

    USERPAYING("用户支付中"),

    PAYERROR("支付失败"),

    UNKNOW("未知状态"),
    ;

    /**
     * 描述 微信退款后有内容
     */
    private String desc;

    ThirdPayOrderStatusEnum(String desc) {
        this.desc = desc;
    }

    public static ThirdPayOrderStatusEnum findByName(String name) {
        for (ThirdPayOrderStatusEnum thirdPayOrderStatusEnum : ThirdPayOrderStatusEnum.values()) {
            if (name.toLowerCase().equals(thirdPayOrderStatusEnum.name().toLowerCase())) {
                return thirdPayOrderStatusEnum;
            }
        }
        throw new RuntimeException("错误的微信支付状态");
    }
}
