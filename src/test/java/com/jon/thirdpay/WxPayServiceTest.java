package com.jon.thirdpay;

import com.jon.thirdpay.config.common.ResponseData;
import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import com.jon.thirdpay.model.*;
import com.jon.thirdpay.model.wxpay.response.WxCreateOrderResponse;
import com.jon.thirdpay.service.ThirdPayService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author testjon 2020/8/3.
 */
public class WxPayServiceTest extends BaseTest {

    @Autowired
    ThirdPayService wxPayAppService;

    @Test

    public void pay() {
        ThirdPayRequest thirdPayRequest = new ThirdPayRequest();
        thirdPayRequest.setOrderAmount(new BigDecimal("13.14"));
        thirdPayRequest.setTradeNo("1");
        thirdPayRequest.setPayTypeEnum(ThirdPayTypeEnum.WXPAY_APP);
        thirdPayRequest.setOrderName("这是一个商品标题");
        ResponseData<WxCreateOrderResponse> pay = wxPayAppService.pay(thirdPayRequest);
        System.out.println(pay);
    }

    @Test
    public void asyncNotify() {
        String demo = "<xml></xml>";
        wxPayAppService.asyncNotify(demo);
    }

    @Test
    public void refund() {
        ThirdPayRefundRequest thirdPayRefundRequest = new ThirdPayRefundRequest();
        thirdPayRefundRequest.setOrderAmount(new BigDecimal("13.14"));
        thirdPayRefundRequest.setTradeNo("1");
        thirdPayRefundRequest.setPayTypeEnum(ThirdPayTypeEnum.WXPAY_APP);
        thirdPayRefundRequest.setOutTradeNo("2");
        wxPayAppService.refund(thirdPayRefundRequest);
    }

    @Test
    public void query() {
        ThirdPayQueryRequest request = new ThirdPayQueryRequest();
        request.setOrderId("1");
        ThirdPayQueryResponse query = wxPayAppService.query(request);
        System.out.println(query);
    }

    @Test
    public void bill() {
        ThirdPayDownloadBillRequest request = new ThirdPayDownloadBillRequest();
        request.setBillDate("20200101");
        request.setPayTypeEnum(ThirdPayTypeEnum.WXPAY_APP);
        String s = wxPayAppService.downloadBill(request);
        System.out.println(s);
    }


}
