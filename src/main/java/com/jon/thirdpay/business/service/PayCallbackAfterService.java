package com.jon.thirdpay.business.service;

import com.jon.thirdpay.business.dto.PayCallbackResultDTO;
import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;

/**
 * 支付回调后处理接口
 *
 * @author testjon 2020/8/14.
 */
public interface PayCallbackAfterService {

    /**
     * 支付回调后处理方法
     * @param dto 支付回调结果
     * @param thirdPayTypeEnum
     */
    void payCallbackAfter(PayCallbackResultDTO dto, ThirdPayTypeEnum thirdPayTypeEnum);
}
