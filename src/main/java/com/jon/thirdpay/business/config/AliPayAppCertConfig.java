package com.jon.thirdpay.business.config;

import com.jon.thirdpay.pay.config.AliPayCertConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 支付宝APP证书支付配置
 *
 * @author testjon 2021-07-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class AliPayAppCertConfig extends AliPayCertConfig {
    @Value("${jon.thirdPay.app.certAliPay.sandbox}")
    @Override
    public void setSandbox(boolean sandbox) {
        super.setSandbox(sandbox);
    }

    @Override
    @Value("${jon.thirdPay.app.certAliPay.appid}")
    public void setAppId(String appId) {
        super.setAppId(appId);
    }

    @Override
    @Value("${jon.thirdPay.app.certAliPay.privateKey}")
    public void setPrivateKey(String privateKey) {
        super.setPrivateKey(privateKey);
    }

    @Override
    @Value("${jon.thirdPay.app.certAliPay.appPublicCertPath}")
    public void setAppPublicCertPath(String appPublicCertPath) {
        super.setAppPublicCertPath(appPublicCertPath);
    }

    @Override
    @Value("${jon.thirdPay.app.certAliPay.alipayPublicCertPath}")
    public void setAlipayPublicCertPath(String alipayPublicCertPath) {
        super.setAlipayPublicCertPath(alipayPublicCertPath);
    }

    @Override
    @Value("${jon.thirdPay.app.certAliPay.alipayRootCertPath}")
    public void setAlipayRootCertPath(String alipayRootCertPath) {
        super.setAlipayRootCertPath(alipayRootCertPath);
    }

    @Override
    public void setNotifyUrl(String notifyUrl) {
        super.setNotifyUrl(notifyUrl);
    }

    @Override
    public void setReturnUrl(String returnUrl) {
        super.setReturnUrl(returnUrl);
    }
}
