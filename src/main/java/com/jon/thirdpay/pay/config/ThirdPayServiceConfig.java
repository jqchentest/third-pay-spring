package com.jon.thirdpay.pay.config;

import com.jon.thirdpay.pay.service.ThirdPayService;
import com.jon.thirdpay.pay.service.impl.alipay.AliPayServiceImpl;
import com.jon.thirdpay.pay.service.impl.wxpay.WxPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方支付接口配置
 * @author testjon 2020/8/4.
 */
@Configuration
public class ThirdPayServiceConfig {

    @Bean(name = "wxPayAppService")
    public ThirdPayService appWxPayService(WxPayAppConfig wxPayAppConfig) {
        return new WxPayServiceImpl(wxPayAppConfig);
    }

    @Bean(name = "aliPayAppService")
    public ThirdPayService aliPayAppService(AliPayAppConfig aliPayAppConfig) {
        return new AliPayServiceImpl(aliPayAppConfig);
    }
}
