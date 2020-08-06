package com.jon.thirdpay.model;

import com.jon.thirdpay.enums.ThirdPayOrderStatusEnum;
import lombok.Builder;
import lombok.Data;

/**
 * 三方支付订单查询 返回结果
 */
@Data
@Builder
public class ThirdPayQueryResponse {

    /**
     * 订单状态
     */
    private ThirdPayOrderStatusEnum thirdPayOrderStatusEnum;

    /**
     *第三方支付的流水号
     */
    private String outTradeNo;

    /**
     * 附加内容，发起支付时传入
     */
    private String attach;

    /**
     * 错误原因
     */
    private String resultMsg;

    /**
     * 订单号
     */
    private String orderId;

    /**
     * 支付完成时间
     */
    private String finishTime;
}
