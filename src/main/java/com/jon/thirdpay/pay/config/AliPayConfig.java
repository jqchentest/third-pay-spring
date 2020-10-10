package com.jon.thirdpay.pay.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付宝支付配置
 * @author testjon 2020-08-05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AliPayConfig extends ThirdPayConfig {
    /**
     * appId
     */
    private String appId;
    /**
     * 商户私钥
     */
    private String privateKey;
    /**
     * 支付宝公钥
     */
    private String aliPayPublicKey;

    /**
     * 获取支付宝API服务器url
     * @return
     */
    public String getAPIServerUrl(){
        return this.isSandbox() ?
                "https://openapi.alipaydev.com/gateway.do" : "https://openapi.alipay.com/gateway.do";
    }

}
