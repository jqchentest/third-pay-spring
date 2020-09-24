package com.jon.thirdpay.model;

import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import lombok.Data;

/**
 * 支付订单查询请求参数
 *
 * @author testjon 2020-08-05
 */
@Data
public class ThirdPayTransferToAccountResponse {
    /**
     * 商户转账唯一订单号
     */
    private String transferNo;

    /**
     * 外部转账单据号
     */
    private String outTransferNo;

    /**
     * 支付时间
     */
    private String payTime;

    public static ThirdPayTransferToAccountResponse create(AlipayFundTransToaccountTransferResponse bean) {
        ThirdPayTransferToAccountResponse response = new ThirdPayTransferToAccountResponse();
        response.setTransferNo(bean.getOutBizNo());
        response.setPayTime(bean.getPayDate());
        response.setOutTransferNo(bean.getOrderId());
        return response;
    }
}
