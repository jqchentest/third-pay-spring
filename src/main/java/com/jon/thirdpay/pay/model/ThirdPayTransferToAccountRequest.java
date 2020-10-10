package com.jon.thirdpay.pay.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付订单查询请求参数
 *
 * @author testjon 2020-08-05
 */
@Data
public class ThirdPayTransferToAccountRequest {
    /**
     * 商户转账唯一订单号
     */
    private String transferNo;
    /**
     * 收款方账户
     */
    private String payeeAccount;
    /**
     * 转账金额
     */
    private BigDecimal transferAmount;
    /**
     * 收款方账户
     */
    private String payeeRealName;
    /**
     * 备注
     */
    private String remark;

}
