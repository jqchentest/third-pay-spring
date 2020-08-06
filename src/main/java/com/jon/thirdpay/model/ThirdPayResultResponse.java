package com.jon.thirdpay.model;

import com.jon.thirdpay.enums.ThirdPayPlatformEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付结果响应类
 * @author testjon 2020/8/3.
 */
@Data
public class ThirdPayResultResponse {
    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 订单号
     */
    private String tradeNo;
    /**
     * 第三方支付的流水号
     */
    private String outTradeNo;

    /**
     * 支付平台
     */
    private ThirdPayPlatformEnum payPlatformEnum;
}
