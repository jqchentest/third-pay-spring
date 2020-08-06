package com.jon.thirdpay.config.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * 微信支付配置类
 * @author testjon 2020-08-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class WxPayConfig extends ThirdPayConfig {

    /**
     * app应用appid
     */
    private String appId;
    /**
     * 公众号appSecret
     */
    private String appSecret;

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户密钥
     */
    private String mchKey;

    /**
     * 商户证书路径
     */
    private String keyPath;
    /**
     * 证书内容
     */
    private SSLContext sslContext;

    /**
     * 初始化证书
     *
     * @return
     */
    public SSLContext initSSLContext() {
        try (FileInputStream inputStream = new FileInputStream(new File(this.getKeyPath()))) {
            KeyStore keystore = KeyStore.getInstance("PKCS12");
            char[] partnerId2charArray = this.getMchId().toCharArray();
            keystore.load(inputStream, partnerId2charArray);
            this.sslContext = SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build();
            return this.sslContext;
        } catch (Exception e) {
            throw new RuntimeException("读取微信商户证书文件出错", e);
        }
    }
}
