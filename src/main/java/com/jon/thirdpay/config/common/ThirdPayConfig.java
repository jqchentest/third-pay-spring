package com.jon.thirdpay.config.common;

import lombok.Data;

/**
 * 第三方支付配置基础类
 */
@Data
public class ThirdPayConfig {

    /**
     * 支付完成后的异步通知地址.
     */
    private String notifyUrl;

    /**
     * 支付完成后的同步返回地址.
     */
    private String returnUrl;

    /**
     * 默认非沙箱测试
     */
    private boolean sandbox = false;
}
