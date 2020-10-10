package com.jon.thirdpay.pay.model;

import com.jon.thirdpay.pay.model.alipay.response.AliPayAsyncResponse;
import com.jon.thirdpay.pay.model.wxpay.response.WxPayAsyncResponse;
import com.jon.thirdpay.pay.utils.MoneyUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付结果响应类
 *
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

    public static ThirdPayResultResponse create(WxPayAsyncResponse response) {
        ThirdPayResultResponse payResponse = new ThirdPayResultResponse();
        payResponse.setOrderAmount(MoneyUtil.Fen2Yuan(response.getTotalFee()));
        payResponse.setTradeNo(response.getOutTradeNo());
        payResponse.setOutTradeNo(response.getTransactionId());
        return payResponse;
    }

    public static ThirdPayResultResponse create(AliPayAsyncResponse response) {
        ThirdPayResultResponse successResponse = new ThirdPayResultResponse();
        successResponse.setOrderAmount(new BigDecimal(response.getTotalAmount()));
        successResponse.setTradeNo(response.getOutTradeNo());
        successResponse.setOutTradeNo(response.getTradeNo());
        return successResponse;
    }

}
