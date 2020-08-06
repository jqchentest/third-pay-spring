package com.jon.thirdpay.model.wxpay.response;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 微信支付 订单查询响应信息
 * Created by 廖师兄
 * 2018-05-31 17:40
 */
@Data
@Root(name = "xml", strict = false)
public class WxOrderQueryResponse {

    @Element(name = "return_code")
    private String returnCode;

    @Element(name = "return_msg", required = false)
    private String returnMsg;

    /**
     * 以下字段在return_code为SUCCESS的时候有返回.
     */
    @Element(name = "appid", required = false)
    private String appid;

    @Element(name = "mch_id", required = false)
    private String mchId;

    @Element(name = "nonce_str", required = false)
    private String nonceStr;

    @Element(name = "sign", required = false)
    private String sign;

    @Element(name = "result_code", required = false)
    private String resultCode;

    @Element(name = "err_code", required = false)
    private String errCode;

    @Element(name = "err_code_des", required = false)
    private String errCodeDes;

    @Element(name = "device_info", required = false)
    private String deviceInfo;

    @Element(name = "openid", required = false)
    private String openid;

    @Element(name = "is_subscribe", required = false)
    private String isSubscribe;

    @Element(name = "trade_type", required = false)
    private String tradeType;

    @Element(name = "trade_state", required = false)
    private String tradeState;

    @Element(name = "bank_type", required = false)
    private String bankType;

    @Element(name = "total_fee", required = false)
    private String totalFee;

    @Element(name = "settlement_total_fee", required = false)
    private String settlementTotalFee;

    @Element(name = "fee_type", required = false)
    private String feeType;

    @Element(name = "cash_fee", required = false)
    private String cashFee;

    @Element(name = "cash_fee_type", required = false)
    private String cashFeeType;

    @Element(name = "coupon_fee", required = false)
    private String couponFee;

    @Element(name = "coupon_count", required = false)
    private String couponCount;

    @Element(name = "transaction_id", required = false)
    private String transactionId;

    @Element(name = "out_trade_no", required = false)
    private String outTradeNo;

    @Element(name = "attach", required = false)
    private String attach;

    @Element(name = "time_end", required = false)
    private String timeEnd;

    @Element(name = "trade_state_desc", required = false)
    private String tradeStateDesc;
}
