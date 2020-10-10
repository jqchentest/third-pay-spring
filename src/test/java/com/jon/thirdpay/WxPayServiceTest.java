package com.jon.thirdpay;

import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;
import com.jon.thirdpay.pay.model.*;
import com.jon.thirdpay.pay.model.wxpay.response.WxCreateOrderResponse;
import com.jon.thirdpay.pay.service.ThirdPayService;
import com.jon.thirdpay.pay.service.ThirdPayServiceFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author testjon 2020/8/3.
 */
public class WxPayServiceTest extends BaseTest {
    @Autowired
    private ThirdPayServiceFactory thirdPayServiceFactory;

    private ThirdPayService getService(){
        return thirdPayServiceFactory.getService(ThirdPayTypeEnum.WXPAY_APP);
    }

    @Test

    public void pay() {
        ThirdPayRequest thirdPayRequest = new ThirdPayRequest();
        thirdPayRequest.setOrderAmount(new BigDecimal("13.14"));
        thirdPayRequest.setTradeNo("1");
        thirdPayRequest.setPayTypeEnum(ThirdPayTypeEnum.WXPAY_APP);
        thirdPayRequest.setOrderTitle("这是一个商品标题");
        WxCreateOrderResponse pay = (WxCreateOrderResponse) getService().pay(thirdPayRequest);
        System.out.println(pay);
    }

    @Test
    public void asyncNotify() {
        String demo = "<xml></xml>";
        getService().asyncNotify(demo);
    }

    @Test
    public void refund() {
        ThirdPayRefundRequest thirdPayRefundRequest = new ThirdPayRefundRequest();
        thirdPayRefundRequest.setRefundAmount(new BigDecimal("13.14"));
        thirdPayRefundRequest.setTradeNo("1");
        thirdPayRefundRequest.setPayTypeEnum(ThirdPayTypeEnum.WXPAY_APP);
        thirdPayRefundRequest.setOutTradeNo("2");
        getService().refund(thirdPayRefundRequest);
    }

    @Test
    public void query() {
        ThirdPayQueryRequest request = new ThirdPayQueryRequest();
        request.setOutTradeNo("1");
        ThirdPayQueryResponse query = getService().query(request);
        System.out.println(query);
    }

    @Test
    public void bill() {
        ThirdPayDownloadBillRequest request = new ThirdPayDownloadBillRequest();
        request.setBillDate("20200101");
        request.setPayTypeEnum(ThirdPayTypeEnum.WXPAY_APP);
        String s = getService().downloadBill(request);
        System.out.println(s);
    }


}
