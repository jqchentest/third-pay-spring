package com.jon.thirdpay.business.service;

import com.jon.thirdpay.business.dto.PayCallbackResultDTO;
import com.jon.thirdpay.common.BusinessException;
import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;
import com.jon.thirdpay.pay.model.ThirdPayResultResponse;
import com.jon.thirdpay.business.config.ThirdPayServiceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 订单支付回调接口
 *
 * @author testjon 2020/8/14.
 */
@Service
@Slf4j
public class OrderPayCallbackService {
    @Autowired
    private ThirdPayServiceFactory thirdPayServiceFactory;

    /**
     * 支付回调
     *
     * @param notifyData 支付回调内容
     * @param payType
     */
    public PayCallbackResultDTO payCallback(String notifyData, ThirdPayTypeEnum payType) {
        log.debug(">>>>>>>>>>>【支付回调接口】, start >>>>>>> notifyData:{},payType:{}", notifyData, payType);
        ThirdPayResultResponse payResult =
                thirdPayServiceFactory.getService(payType).asyncNotify(notifyData);
        if (payResult == null) {
            log.warn(">>>>>>>>>>>【支付回调接口】, 未支付成功 notifyData:{},payType:{}", notifyData, payType);
            throw new BusinessException("未支付成功");
        }

        //TODO 具体业务
        return null;
    }
}
