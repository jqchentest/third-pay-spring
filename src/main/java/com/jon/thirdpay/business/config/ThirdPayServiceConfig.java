package com.jon.thirdpay.business.config;

import com.jon.thirdpay.pay.service.ThirdPayService;
import com.jon.thirdpay.pay.service.impl.alipay.AliPayCertServiceImpl;
import com.jon.thirdpay.pay.service.impl.alipay.AliPayServiceImpl;
import com.jon.thirdpay.pay.service.impl.wxpay.WxPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方支付接口配置
 *
 * @author testjon 2021-07-14
 */
@Configuration
public class ThirdPayServiceConfig {

    @Bean(name = "wxPayAppService")
    public ThirdPayService appWxPayService(WxPayAppConfig wxPayAppConfig) {
        return new WxPayServiceImpl(wxPayAppConfig);
    }

    /**
     * 普通秘钥方式
     */
    @Bean(name = "aliPayAppService")
    public ThirdPayService aliPayAppService(AliPayAppConfig aliPayAppConfig) {
        return new AliPayServiceImpl(aliPayAppConfig);
    }

    /**
     * 证书方式
     */
    @Bean(name = "aliPayAppCertService")
    public ThirdPayService aliPayAppCertService(AliPayAppCertConfig config) {
        return new AliPayCertServiceImpl(config);
    }
}
