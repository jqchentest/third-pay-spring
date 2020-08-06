package com.jon.thirdpay.service;

import com.jon.thirdpay.config.common.ResponseData;
import com.jon.thirdpay.enums.SignType;
import com.jon.thirdpay.model.*;

import java.util.Map;

/**
 * 支付相关
 * @author testjon 2020-08-05
 */
public interface ThirdPayService {

    /**
     * 发起支付.
     */
    ResponseData pay(ThirdPayRequest request);

    /**
     * 验证支付结果. 包括同步和异步.
     *
     * @param toBeVerifiedParamMap 待验证的支付结果参数.
     * @param signType             签名方式.
     * @param sign                 签名.
     * @return 验证结果.
     */
    boolean verify(Map<String, String> toBeVerifiedParamMap, SignType signType, String sign);

    /**
     * 异步回调
     * @param notifyData
     * @return
     */
    ThirdPayResultResponse asyncNotify(String notifyData);

    /**
     * 退款
     * @param request
     * @return
     */
    ThirdPayRefundResponse refund(ThirdPayRefundRequest request);

    /**
     * 查询订单
     * @param request
     * @return
     */
    ThirdPayQueryResponse query(ThirdPayQueryRequest request);


    /**
     * 下载对账单
     * @param request
     * @return
     */
    String downloadBill(ThirdPayDownloadBillRequest request);


    /**
     * 根据规则生成二维码URL
     * @param productId 商品ID
     * @return 二维码中的内容为链接
     */
    String getQrCodeUrl(String productId);

}
