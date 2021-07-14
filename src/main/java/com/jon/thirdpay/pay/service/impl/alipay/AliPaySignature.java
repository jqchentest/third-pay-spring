package com.jon.thirdpay.pay.service.impl.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author testjon 2020-08-06
 */
@Slf4j
public class AliPaySignature {

    /**
     * 校验签名
     *
     * @param params
     * @param publicKey
     * @return
     */
    public static Boolean verify(Map<String, String> params, String publicKey) throws RuntimeException {
        try {
            return AlipaySignature.rsaCheckV1(params, publicKey, "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            log.error("【支付宝支付校验签名】, 发生异常 params:{}, publicKey:{}, e:", params, publicKey, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据证书校验签名
     *
     * @param params
     * @param alipayPublicCertPath
     * @return
     */
    public static Boolean certVerify(Map<String, String> params, String alipayPublicCertPath) throws RuntimeException {
        try {
            return AlipaySignature.rsaCertCheckV1(params, alipayPublicCertPath, "UTF-8", "RSA2");
        } catch (AlipayApiException e) {
            log.error("【支付宝支付校验签名】, 发生异常 params:{}, alipayPublicCertPath:{}, e:", params, alipayPublicCertPath, e);
            throw new RuntimeException(e);
        }
    }

}
