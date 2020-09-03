package com.jon.thirdpay.model.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付回调结果dto
 *
 * @author testjon 2020/8/14.
 */
@Data
public class PayCallbackResultDTO {

    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 订单主键id
     */
    private Integer orderId;

    /**
     * 订单号
     */
    private String tradeNo;
    /**
     * 外部订单号
     */
    private String outTradeNo;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;

    /**
     * 商品数量
     */
    private Integer itemAmount;
    /**
     * 标题
     */
    private String title;
    /**
     * 卖家ID
     */
    private Integer sellerId;

}
