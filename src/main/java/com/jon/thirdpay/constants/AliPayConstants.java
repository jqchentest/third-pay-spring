/**
 * Copyright (c) 2015, 59store. All rights reserved.
 */
package com.jon.thirdpay.constants;

/**
 * 支付宝常量
 * @author testjon 2020-08-05
 */
public interface AliPayConstants {

    /**
     * 商户签约的产品支持退款功能的前提下，买家付款成功；
     */
    String TRADE_SUCCESS = "TRADE_SUCCESS";

    /**
     * 商户签约的产品不支持退款功能的前提下，买家付款成功；或者，商户签约的产品支持退款功能的前提下，交易已经成功并且已经超过可退款期限。
     */
    String TRADE_FINISHED  = "TRADE_FINISHED";
}
