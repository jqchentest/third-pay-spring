package com.jon.thirdpay.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 三方退款 返回结果
 */
@Data
public class ThirdPayRefundResponse {

    /**
     * 订单号.
     */
    private String tradeNo;

    /**
     * 订单金额.
     */
    private BigDecimal orderAmount;

    /**
     * 第三方支付流水号.
     */
    private String outTradeNo;

    /**
     * 退款号.
     */
    private String refundNo;

    /**
     * 第三方退款流水号.
     */
    private String outRefundNo;


}
