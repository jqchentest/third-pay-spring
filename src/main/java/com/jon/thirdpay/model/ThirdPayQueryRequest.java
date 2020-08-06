package com.jon.thirdpay.model;

import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import lombok.Data;

/**
 * 支付订单查询请求参数
 */
@Data
public class ThirdPayQueryRequest {

    /**
     * 支付平台.
     */
    private ThirdPayTypeEnum payTypeEnum;

    /**
     * 订单号(orderId 和 outOrderId 二选一，两个都传以outOrderId为准)
     */
    private String orderId;

    /**
     * 第三方交易号
     */
    private String outTradeNo;
}
