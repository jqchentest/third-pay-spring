package com.jon.thirdpay.business.config;

import com.jon.thirdpay.common.BusinessException;
import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;
import com.jon.thirdpay.pay.service.ThirdPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author testjon 2021-07-14
 */
@Component
public class ThirdPayServiceFactory {
    @Autowired
    private Map<String, ThirdPayService> services;

    /**
     * 根据第三方支付类型获取对应实现类
     *
     * @param param
     */
    public ThirdPayService getService(ThirdPayTypeEnum param) {
        if (ThirdPayTypeEnum.WXPAY_APP.equals(param)) {
            return services.get("wxPayAppService");
        }
//        普通秘钥方式
//        if (ThirdPayTypeEnum.ALIPAY_APP.equals(param)) {
//            return services.get("aliPayAppService");
//        }
        //证书方式
        if (ThirdPayTypeEnum.ALIPAY_APP.equals(param)) {
            return services.get("aliPayAppCertService");
        }
        throw new BusinessException("支付平台错误");
    }
}
