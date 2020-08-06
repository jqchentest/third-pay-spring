package com.jon.thirdpay.config.common;

import com.jon.thirdpay.config.AliPayAppConfig;
import com.jon.thirdpay.config.WxPayAppConfig;
import com.jon.thirdpay.config.WxPayMiniConfig;
import com.jon.thirdpay.service.ThirdPayService;
import com.jon.thirdpay.service.impl.alipay.AliPayServiceImpl;
import com.jon.thirdpay.service.impl.wxpay.WxPayServiceImpl;
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

    @Bean(name = "wxPayMiniService")
    public ThirdPayService wxPayMiniService(WxPayMiniConfig wxPayMiniConfig) {
        return new WxPayServiceImpl(wxPayMiniConfig);
    }
}
