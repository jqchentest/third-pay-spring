package com.jon.thirdpay.pay.model;

import com.alipay.api.response.AlipayTradeRefundResponse;
import com.jon.thirdpay.pay.model.wxpay.response.WxRefundResponse;
import com.jon.thirdpay.pay.utils.MoneyUtil;
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
     * 退款金额.
     */
    private BigDecimal refundAmount;

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

    /**
     * 买家外部用户id
     */
    private String outBuyerUserId;

    /**
     * 买家外部的登录id
     */
    private String outBuyerLoginId;

    public static ThirdPayRefundResponse create(WxRefundResponse response) {
        ThirdPayRefundResponse thirdPayRefundResponse = new ThirdPayRefundResponse();
        thirdPayRefundResponse.setTradeNo(response.getOutTradeNo());
        thirdPayRefundResponse.setRefundAmount(MoneyUtil.Fen2Yuan(response.getTotalFee()));
        thirdPayRefundResponse.setOutTradeNo(response.getTransactionId());
        thirdPayRefundResponse.setRefundNo(response.getOutRefundNo());
        thirdPayRefundResponse.setOutRefundNo(response.getOutRefundNo());
        return thirdPayRefundResponse;
    }

    public static ThirdPayRefundResponse create(AlipayTradeRefundResponse response) {
        ThirdPayRefundResponse thirdPayRefundResponse = new ThirdPayRefundResponse();
        thirdPayRefundResponse.setTradeNo(response.getOutTradeNo());
        thirdPayRefundResponse.setRefundAmount(new BigDecimal(response.getRefundFee()));
        thirdPayRefundResponse.setOutTradeNo(response.getTradeNo());
        thirdPayRefundResponse.setOutBuyerLoginId(response.getBuyerLogonId());
        thirdPayRefundResponse.setOutBuyerUserId(response.getBuyerUserId());
        return thirdPayRefundResponse;
    }
}
