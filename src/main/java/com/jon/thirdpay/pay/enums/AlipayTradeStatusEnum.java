package com.jon.thirdpay.pay.enums;

import lombok.Getter;

/**
 * 支付宝交易状态枚举
 * @author testjon 2020-08-05
 */
@Getter
public enum AlipayTradeStatusEnum {

    /** 交易创建，等待买家付款。 */
    WAIT_BUYER_PAY(ThirdPayOrderStatusEnum.NOTPAY),

    /**
     * <pre>
     * 在指定时间段内未支付时关闭的交易；
     * 在交易完成全额退款成功时关闭的交易。
     * </pre>
     */
    TRADE_CLOSED(ThirdPayOrderStatusEnum.CLOSED),

    /** 交易成功，且可对该交易做操作，如：多级分润、退款等。 */
    TRADE_SUCCESS(ThirdPayOrderStatusEnum.SUCCESS),

    /** 等待卖家收款（买家付款后，如果卖家账号被冻结）。 */
    TRADE_PENDING(ThirdPayOrderStatusEnum.NOTPAY),

    /** 交易成功且结束，即不可再做任何操作。 */
    TRADE_FINISHED(ThirdPayOrderStatusEnum.SUCCESS),

    ;

    private ThirdPayOrderStatusEnum thirdPayOrderStatusEnum;

    AlipayTradeStatusEnum(ThirdPayOrderStatusEnum thirdPayOrderStatusEnum) {
        this.thirdPayOrderStatusEnum = thirdPayOrderStatusEnum;
    }

    public static AlipayTradeStatusEnum findByName(String name) {
        for (AlipayTradeStatusEnum statusEnum : AlipayTradeStatusEnum.values()) {
            if (name.toLowerCase().equals(statusEnum.name().toLowerCase())) {
                return statusEnum;
            }
        }
        throw new RuntimeException("错误的支付宝支付状态");
    }
}
