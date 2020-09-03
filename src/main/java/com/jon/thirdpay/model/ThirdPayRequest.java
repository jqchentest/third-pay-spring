package com.jon.thirdpay.model;

import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付时请求参数
 */
@Data
public class ThirdPayRequest {

    /**
     * 支付方式.
     */
    private ThirdPayTypeEnum payTypeEnum;
    /**
     * 订单号.
     */
    private String tradeNo;

    /**
     * 订单金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单名字.
     */
    private String orderTitle;

    /**
     * 微信openid, 仅微信公众号/小程序支付时需要
     */
    private String openid;

    /**
     * 客户端访问Ip  外部H5支付时必传，需要真实Ip
     * 20191015测试，微信h5支付已不需要真实的ip
     */
    private String spbillCreateIp;

    /**
     * 附加内容，发起支付时传入；可选
     */
    private String attach;

}
