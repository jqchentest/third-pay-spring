package com.jon.thirdpay.service.impl.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.jon.thirdpay.common.BusinessException;
import com.jon.thirdpay.common.ResponseData;
import com.jon.thirdpay.config.AliPayConfig;
import com.jon.thirdpay.constants.AliPayConstants;
import com.jon.thirdpay.enums.AlipayTradeStatusEnum;
import com.jon.thirdpay.enums.SignType;
import com.jon.thirdpay.model.*;
import com.jon.thirdpay.model.alipay.response.AliPayAsyncResponse;
import com.jon.thirdpay.service.ThirdPayService;
import com.jon.thirdpay.utils.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by this on 2019/9/8 15:50
 */
@Slf4j
public class AliPayServiceImpl implements ThirdPayService {
    /**
     * 仅支持JSON
     */
    private static final String FORMAT = "json";
    /**
     * 请求使用的编码格式，如utf-8,gbk,gb2312等
     */
    private static final String CHARSET = "utf-8";
    /**
     * 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2
     */
    private static final String SIGN_TYPE = "RSA2";
    /**
     * 该笔订单允许的最晚付款时间 30分钟；<br/>
     * 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
     */
    private static final String TIMEOUT_EXPRESS = "30m";
    /**
     * 销售产品码，商家和支付宝签约的产品码
     */
    private static final String PRODUCT_CODE = "QUICK_MSECURITY_PAY";

    /**
     * 支付宝支付配置
     */
    private final AliPayConfig aliPayConfig;

    public AliPayServiceImpl(AliPayConfig aliPayConfig) {
        this.aliPayConfig = aliPayConfig;
    }

    @Override
    public ResponseData<String> pay(ThirdPayRequest thirdPayRequest) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getAPIServerUrl(), aliPayConfig.getAppId(),
                aliPayConfig.getPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAliPayPublicKey(), SIGN_TYPE);
        //实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        //SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        //model.setBody("商品详情");
        model.setSubject(thirdPayRequest.getOrderTitle());
        model.setOutTradeNo(thirdPayRequest.getTradeNo());
        model.setTimeoutExpress(TIMEOUT_EXPRESS);
        model.setTotalAmount(thirdPayRequest.getOrderAmount().toString());
        model.setProductCode(PRODUCT_CODE);
        request.setBizModel(model);
        request.setNotifyUrl(aliPayConfig.getNotifyUrl());
        try {
            //这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse sdkResult = alipayClient.sdkExecute(request);
            // pc 网站支付 只需返回body
            return ResponseData.success(sdkResult.getBody());
        } catch (Exception e) {
            log.error("【生成支付宝APP支付请求体】 发生异常 request:{}, e:", thirdPayRequest, e);
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean verify(Map<String, String> toBeVerifiedParamMap, SignType signType, String sign) {
        return AliPaySignature.verify(toBeVerifiedParamMap, aliPayConfig.getAliPayPublicKey());
    }

    /**
     * 异步通知
     *
     * @param notifyData
     * @return
     */
    @Override
    public ThirdPayResultResponse asyncNotify(String notifyData) {
        try {
            notifyData = URLDecoder.decode(notifyData, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("【支付宝支付异步通知】回调内容有误 notifyData:{}, e:{}", notifyData, e);
            throw new RuntimeException(e);
        }
        //签名校验
        if (!AliPaySignature.verify(MapUtil.form2Map(notifyData), aliPayConfig.getAliPayPublicKey())) {
            log.error("【支付宝支付异步通知】签名验证失败, response={}", notifyData);
            throw new BusinessException("【支付宝支付异步通知】签名验证失败");
        }
        HashMap<String, String> params = MapUtil.form2MapWithCamelCase(notifyData);
        AliPayAsyncResponse response = MapUtil.mapToObject(params, AliPayAsyncResponse.class);
        assert response != null;
        String tradeStatus = response.getTradeStatus();
        if (!tradeStatus.equals(AliPayConstants.TRADE_FINISHED) &&
                !tradeStatus.equals(AliPayConstants.TRADE_SUCCESS)) {
            log.warn("【支付宝支付异步通知】订单未支付 notifyData:{},response:{}", notifyData, response);
            throw new BusinessException("【支付宝支付异步通知】订单未支付");
        }
        return ThirdPayResultResponse.create(response);
    }

    @Override
    public ThirdPayRefundResponse refund(ThirdPayRefundRequest thirdPayRefundRequest) {
        //实例化客户端
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getAPIServerUrl(), aliPayConfig.getAppId(),
                aliPayConfig.getPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAliPayPublicKey(), SIGN_TYPE);
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_trade_no", thirdPayRefundRequest.getTradeNo());
        jsonObject.put("trade_no", thirdPayRefundRequest.getOutTradeNo());
        jsonObject.put("refund_amount", thirdPayRefundRequest.getRefundAmount());
        request.setBizContent(jsonObject.toJSONString());
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            log.error("【支付宝退款】 发生异常 e:", e);
            throw new RuntimeException(e);
        }
        if (!response.isSuccess()) {
            log.warn("【支付宝退款】退款失败, request:{}, response:{}", thirdPayRefundRequest, response);
            throw new BusinessException("支付宝退款失败,请稍后再试");
        }
        return ThirdPayRefundResponse.create(response);
    }

    @Override
    public ThirdPayQueryResponse query(ThirdPayQueryRequest queryRequest) {
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getAPIServerUrl(), aliPayConfig.getAppId(),
                    aliPayConfig.getPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAliPayPublicKey(), SIGN_TYPE);
            AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("out_trade_no", queryRequest.getTradeNo());
            jsonObject.put("trade_no", queryRequest.getOutTradeNo());
//        jsonObject.put("org_pid","");

            request.setBizContent(jsonObject.toJSONString());
            AlipayTradeQueryResponse response = alipayClient.execute(request);
            if (!response.isSuccess()) {
                log.warn("【支付宝查询交易】, 查询失败 query:{}, response:{}", queryRequest, response);
                return null;
            }
            String finishTime = null;
            if (response.getSendPayDate() != null) {
                finishTime =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.ofInstant(response.getSendPayDate().toInstant(), ZoneId.systemDefault()));
            }
            return ThirdPayQueryResponse.builder()
                    .thirdPayOrderStatusEnum(AlipayTradeStatusEnum.findByName(response.getTradeStatus()).getThirdPayOrderStatusEnum())
                    .outTradeNo(response.getTradeNo())
                    .orderId(response.getOutTradeNo())
                    .resultMsg(response.getMsg())
                    .finishTime(finishTime)
                    .build();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("【支付宝查询交易】, 发生异常 query:{} , e:", queryRequest, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String downloadBill(ThirdPayDownloadBillRequest queryRequest) {
        try {
            AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getAPIServerUrl(), aliPayConfig.getAppId(),
                    aliPayConfig.getPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAliPayPublicKey(), SIGN_TYPE);
            AlipayDataDataserviceBillDownloadurlQueryRequest request =
                    new AlipayDataDataserviceBillDownloadurlQueryRequest();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("bill_type", "trade");
            jsonObject.put("bill_date", queryRequest.getBillDate());
            request.setBizContent(jsonObject.toJSONString());
            AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
            if (!response.isSuccess()) {
                log.warn("【支付宝对账单查询】, 查询失败 query:{}", queryRequest);
                throw new BusinessException("支付宝对账单查询失败");
            }
            return response.getBillDownloadUrl();

        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("【支付宝对账单查询】, 发生异常 query:{} , e:", queryRequest, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getQrCodeUrl(String productId) {
        return null;
    }

    @Override
    public ThirdPayTransferToAccountResponse transferToAccount(ThirdPayTransferToAccountRequest request) throws AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayConfig.getAPIServerUrl(), aliPayConfig.getAppId(),
                aliPayConfig.getPrivateKey(), FORMAT, CHARSET, aliPayConfig.getAliPayPublicKey(), SIGN_TYPE);
        AlipayFundTransToaccountTransferRequest alipayRequest = new AlipayFundTransToaccountTransferRequest();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("out_biz_no", request.getTransferNo());
        jsonObject.put("payee_type", "ALIPAY_LOGONID");
        jsonObject.put("payee_account", request.getPayeeAccount());
        jsonObject.put("amount", request.getTransferAmount());
        jsonObject.put("payee_real_name", request.getPayeeRealName());
        jsonObject.put("remark", request.getRemark());

        alipayRequest.setBizContent(jsonObject.toJSONString());
        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(alipayRequest);
        if (!response.isSuccess()) {
            log.warn("【转账至支付宝账户】, 转账失败 query:{}", response);
            if (!StringUtils.isEmpty(response.getSubMsg())) {
                throw new BusinessException(response.getSubMsg());
            }
            throw new BusinessException("转账至支付宝账户失败");
        }
        return ThirdPayTransferToAccountResponse.create(response);
    }
}
