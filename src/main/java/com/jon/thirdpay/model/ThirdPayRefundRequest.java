package com.jon.thirdpay.model;

import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 三方支付时请求参数
 */
@Data
public class ThirdPayRefundRequest {

    /**
     * 支付方式.
     */
    private ThirdPayTypeEnum payTypeEnum;

    /**
     * 订单号.
     */
    private String tradeNo;

    /**
     * 第三方交易号
     */
    private String outTradeNo;

    /**
     * 退款金额.
     */
    private BigDecimal refundAmount;

    /**
     * 用户ID
     */
    private Integer userId;
}
