package com.jon.thirdpay;

import com.jon.thirdpay.common.ResponseData;
import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import com.jon.thirdpay.model.ThirdPayRequest;
import com.jon.thirdpay.service.ThirdPayService;
import com.jon.thirdpay.service.ThirdPayServiceFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author testjon 2020-08-06
 */
public class AliPayTest extends BaseTest{
    @Autowired
    private ThirdPayServiceFactory thirdPayServiceFactory;

    private ThirdPayService getService(){
        return thirdPayServiceFactory.getService(ThirdPayTypeEnum.ALIPAY_APP);
    }

    @Test
    public void pay(){
        ThirdPayRequest thirdPayRequest = new ThirdPayRequest();
        thirdPayRequest.setOrderAmount(new BigDecimal("13.14"));
        thirdPayRequest.setTradeNo("123");
        thirdPayRequest.setPayTypeEnum(ThirdPayTypeEnum.ALIPAY_APP);
        thirdPayRequest.setOrderTitle("这是一个商品标题");
        ResponseData pay = getService().pay(thirdPayRequest);
        Assert.assertNotNull(pay.getData());
        System.out.println(pay);
    }
}
