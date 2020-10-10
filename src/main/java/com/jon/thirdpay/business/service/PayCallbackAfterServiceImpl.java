package com.jon.thirdpay.business.service;

import com.jon.thirdpay.business.dto.PayCallbackResultDTO;
import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;
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
