package com.jon.thirdpay.pay.service;

import com.jon.thirdpay.common.BusinessException;
import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author testjon 2020/8/14.
 */
@Component
public class ThirdPayServiceFactory {
    @Autowired
    private Map<String ,ThirdPayService> services;

    /**
     * 根据第三方支付类型获取对应实现类
     *
     * @param param
     */
    public ThirdPayService getService(ThirdPayTypeEnum param) {
        if (ThirdPayTypeEnum.WXPAY_APP.equals(param)) {
            return services.get("wxPayAppService");
        }
        if (ThirdPayTypeEnum.ALIPAY_APP.equals(param)) {
            return services.get("aliPayAppService");
        }
        throw new BusinessException("支付平台错误");
    }
}
