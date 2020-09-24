package com.jon.thirdpay.model;

import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import lombok.Data;

/**
 * 支付订单查询请求参数
 * @author testjon 2020-08-05
 */
@Data
public class ThirdPayQueryRequest {

    /**
     * 支付平台.
     */
    private ThirdPayTypeEnum payTypeEnum;

    /**
     * 订单号(tradeNo 和 outTradeNo 二选一，两个都传以outTradeNo为准)
     */
    private String tradeNo;

    /**
     * 第三方交易号
     */
    private String outTradeNo;
}
