package com.jon.thirdpay.business.service;

import com.jon.thirdpay.business.dto.PayCallbackResultDTO;
import com.jon.thirdpay.common.BusinessException;
import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付回调
 *
 * @author testjon 2020/8/6.
 */
@Service
@Slf4j
public class PayCallbackService {
    @Autowired
    private OrderPayCallbackService orderPayCallbackService;
    @Autowired
    private PayCallbackAfterService  payCallbackAfterService;

    /**
     * 支付回调接口
     */
    public void payCallback(String notifyData, ThirdPayTypeEnum thirdPayTypeEnum) {
        PayCallbackResultDTO dto = orderPayCallbackService.payCallback(notifyData, thirdPayTypeEnum);
        if (dto == null) {
            log.warn(">>>>>>>>>>>【活动订单支付回调】, 未支付成功 notifyData:{},payType:{}", notifyData, thirdPayTypeEnum);
            throw new BusinessException("未支付成功");
        }
        //调用支付回调后处理接口
        payCallbackAfterService.payCallbackAfter(dto, thirdPayTypeEnum);
    }
}
