package com.jon.thirdpay.config;

import com.jon.thirdpay.config.common.WxPayConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 微信小程序支付配置
 *
 * @author testjon 2020-08-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Component
public class WxPayMiniConfig extends WxPayConfig {
    @Override
    @Value("${road.thirdPay.mini.wxPay.appId}")
    public void setAppId(String appId) {
        super.setAppId(appId);
    }

    @Override
    @Value("${road.thirdPay.mini.wxPay.appSecret}")
    public void setAppSecret(String appSecret) {
        super.setAppSecret(appSecret);
    }

    @Override
    @Value("${road.thirdPay.mini.wxPay.mchId}")
    public void setMchId(String mchId) {
        super.setMchId(mchId);
    }

    @Override
    @Value("${road.thirdPay.mini.wxPay.mchKey}")
    public void setMchKey(String mchKey) {
        super.setMchKey(mchKey);
    }

    @Override
    @Value("${road.thirdPay.mini.wxPay.keyPath}")
    public void setKeyPath(String keyPath) {
        super.setKeyPath(keyPath);
    }

    @Value("${road.thirdPay.mini.wxPay.sandbox}")
    @Override
    public void setSandbox(boolean sandbox) {
        super.setSandbox(sandbox);
    }

    @Value("${road.thirdPay.mini.wxPay.notifyUrl}")
    @Override
    public void setNotifyUrl(String notifyUrl) {
        super.setNotifyUrl(notifyUrl);
    }

//    /**
//     * 初始化证书
//     *
//     * @return
//     */
//    @Override
//    public SSLContext initSSLContext() {
//        try (FileInputStream inputStream = new FileInputStream(new File(this.getKeyPath()))) {
//            KeyStore keystore = KeyStore.getInstance("PKCS12");
//            char[] partnerId2charArray = this.getMchId().toCharArray();
//            keystore.load(inputStream, partnerId2charArray);
//            this.setSslContext(SSLContexts.custom().loadKeyMaterial(keystore, partnerId2charArray).build());
//            return this.getSslContext();
//        } catch (Exception e) {
//            throw new RuntimeException("读取微信商户证书文件出错", e);
//        }
//    }

}
