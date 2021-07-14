package com.jon.thirdpay.business.config;

import com.jon.thirdpay.pay.config.AliPayConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * 支付宝APP支付配置
 *
 * @author testjon 2021-07-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class AliPayAppConfig extends AliPayConfig {


    @Override
    @Value("${jon.thirdPay.app.aliPay.appid}")
    public void setAppId(String appId) {
        super.setAppId(appId);
    }

    @Override
    @Value("${jon.thirdPay.app.aliPay.privateKey}")
    public void setPrivateKey(String privateKey) {
        super.setPrivateKey(privateKey);
    }

    @Override
    @Value("${jon.thirdPay.app.aliPay.publicKey}")
    public void setAliPayPublicKey(String aliPayPublicKey) {
        super.setAliPayPublicKey(aliPayPublicKey);
    }

    @Value("${jon.thirdPay.app.aliPay.sandbox}")
    @Override
    public void setSandbox(boolean sandbox) {
        super.setSandbox(sandbox);
    }

    @Value("${jon.thirdPay.app.aliPay.notifyUrl}")
    @Override
    public void setNotifyUrl(String notifyUrl) {
        super.setNotifyUrl(notifyUrl);
    }

    @Override
    public void setReturnUrl(String returnUrl) {
        super.setReturnUrl(returnUrl);
    }
}
