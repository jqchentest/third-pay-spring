package com.jon.thirdpay.model.wxpay.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 微信统一下单返回结果
 * @author testjon 2020-08-05
 */
@Data
public class WxCreateOrderResponse {

    /**
     * 应用ID
     */
    private String appId;
    /**
     * 商户号
     */
    private String partnerId;
    /**
     * 微信返回的支付交易会话ID
     */
    private String prepayId;
    /**
     * 扩展字段, 暂填写固定值Sign=WXPay
     */
    @JsonProperty("package")
    private String packAge;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 签名
     */
    private String sign;

}
