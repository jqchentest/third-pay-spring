package com.jon.thirdpay.service.impl;

import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import com.jon.thirdpay.model.dto.PayCallbackResultDTO;
import com.jon.thirdpay.service.PayCallbackAfterService;
import org.springframework.stereotype.Service;

/**
 * 支付回调后处理接口
 * @author testjon 2020/9/3.
 */
@Service
public class PayCallbackAfterServiceImpl implements PayCallbackAfterService {
    @Override
    public void payCallbackAfter(PayCallbackResultDTO dto, ThirdPayTypeEnum thirdPayTypeEnum) {
        //TODO 具体业务
    }
}
