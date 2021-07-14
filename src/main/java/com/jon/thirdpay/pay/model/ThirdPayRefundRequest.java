package com.jon.thirdpay.pay.model;

import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 三方支付时请求参数
 *
 * @author testjon 2021-07-14
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
    private BigDecimal orderAmount;

    /**
     * 退款金额.
     */
    private BigDecimal refundAmount;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 部分退款；
     * 标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传。
     */
    private String partialRefundNo;
}
