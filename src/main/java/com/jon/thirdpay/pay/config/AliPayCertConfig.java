package com.jon.thirdpay.pay.config;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付宝支付证书模式配置
 *
 * @author testjon 2021-07-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AliPayCertConfig extends ThirdPayConfig {
    /**
     * appId
     */
    private String appId;
    /**
     * 商户私钥
     */
    private String privateKey;
    /**
     * 应用公钥证书路径
     */
    private String appPublicCertPath;
    /**
     * 支付宝公钥证书文件路
     */
    private String alipayPublicCertPath;
    /**
     * 支付宝 CA 根证书文件路径
     */
    private String alipayRootCertPath;

    /**
     * 获取支付宝API服务器url
     *
     * @return
     */
    public String getAPIServerUrl() {
        return this.isSandbox() ?
                "https://openapi.alipaydev.com/gateway.do" : "https://openapi.alipay.com/gateway.do";
    }

}
