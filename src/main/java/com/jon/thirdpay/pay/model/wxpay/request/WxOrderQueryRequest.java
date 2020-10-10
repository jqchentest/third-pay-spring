package com.jon.thirdpay.pay.model.wxpay.request;

import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * 微信支付查询订单请求参数
 *
 */
@Data
@Root(name = "xml", strict = false)
public class WxOrderQueryRequest {

    @Element(name = "appid")
    private String appid;

    @Element(name = "mch_id")
    private String mchId;

    @Element(name = "transaction_id", required = false)
    private String transactionId;

    @Element(name = "out_trade_no", required = false)
    private String outTradeNo;

    @Element(name = "nonce_str")
    private String nonceStr;

    @Element(name = "sign")
    private String sign;

    @Element(name = "sign_type", required = false)
    private String signType;
}
