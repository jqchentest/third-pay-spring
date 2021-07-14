package com.jon.thirdpay.pay.service.impl.wxpay;

import com.jon.thirdpay.common.BusinessException;
import com.jon.thirdpay.pay.config.WxPayConfig;
import com.jon.thirdpay.pay.constants.WxPayConstants;
import com.jon.thirdpay.pay.enums.SignType;
import com.jon.thirdpay.pay.enums.ThirdPayOrderStatusEnum;
import com.jon.thirdpay.pay.model.*;
import com.jon.thirdpay.pay.model.wxpay.WxPayApi;
import com.jon.thirdpay.pay.model.wxpay.request.WxDownloadBillRequest;
import com.jon.thirdpay.pay.model.wxpay.request.WxOrderQueryRequest;
import com.jon.thirdpay.pay.model.wxpay.request.WxPayRefundRequest;
import com.jon.thirdpay.pay.model.wxpay.request.WxPayUnifiedorderRequest;
import com.jon.thirdpay.pay.model.wxpay.response.*;
import com.jon.thirdpay.pay.service.ThirdPayService;
import com.jon.thirdpay.pay.utils.MapUtil;
import com.jon.thirdpay.pay.utils.MoneyUtil;
import com.jon.thirdpay.pay.utils.RandomUtil;
import com.jon.thirdpay.pay.utils.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.util.StringUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信支付
 */
@Slf4j
public class WxPayServiceImpl implements ThirdPayService {

    private final WxPayConfig wxPayConfig;

    public WxPayServiceImpl(WxPayConfig wxPayConfig) {
        this.wxPayConfig = wxPayConfig;
    }

    private Retrofit getRetrofit() {
        return wxPayConfig.isSandbox() ? DEV_RETROFIT : RETROFIT;
    }

    private Retrofit getSslContextRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .sslSocketFactory(wxPayConfig.getSslContext().getSocketFactory())
                .addInterceptor((new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)))
                .build();
        return getRetrofit().newBuilder().client(okHttpClient).build();
    }

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(WxPayConstants.WXPAY_GATEWAY)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .build();
    private static final Retrofit DEV_RETROFIT = new Retrofit.Builder()
            .baseUrl(WxPayConstants.WXPAY_GATEWAY_DEV)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
            .build();


    @Override
    public WxCreateOrderResponse pay(ThirdPayRequest request) {
        try {
            WxPayUnifiedorderRequest wxRequest = new WxPayUnifiedorderRequest();
            wxRequest.setOutTradeNo(request.getTradeNo());
            wxRequest.setTotalFee(MoneyUtil.Yuan2Fen(request.getOrderAmount()));
            wxRequest.setBody(getOrderTitle(request.getOrderTitle(), request.getTradeNo()));
            wxRequest.setOpenid(request.getOpenid());
            wxRequest.setTradeType(request.getPayTypeEnum().getCode());
            wxRequest.setAppid(wxPayConfig.getAppId());
            wxRequest.setMchId(wxPayConfig.getMchId());
            wxRequest.setNotifyUrl(wxPayConfig.getNotifyUrl());
            wxRequest.setNonceStr(RandomUtil.getRandomStr());
            wxRequest.setSpbillCreateIp(StringUtils.isEmpty(request.getSpbillCreateIp()) ? "8.8.8.8" :
                    request.getSpbillCreateIp());
            wxRequest.setAttach(request.getAttach());
            wxRequest.setSign(WxPaySignature.sign(MapUtil.buildMap(wxRequest), wxPayConfig.getMchKey()));

            RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),
                    XmlUtil.toString(wxRequest));
            Call<WxUnifiedOrderResponse> call = getRetrofit().create(WxPayApi.class).unifiedorder(body);
            Response<WxUnifiedOrderResponse> retrofitResponse = call.execute();
            if (!retrofitResponse.isSuccessful()) {
                log.warn("【微信统一下单】,调用失败 request:{}, response:{}", request, retrofitResponse);
                throw new BusinessException("微信统一下单失败, 网络异常");
            }
            WxUnifiedOrderResponse response = retrofitResponse.body();

            assert response != null;
            if (!response.getReturnCode().equals(WxPayConstants.SUCCESS) || !response.getResultCode().equals(WxPayConstants.SUCCESS)) {
                log.warn("【微信统一下单】,下单失败 request:{}, response:{}", request, response);
                throw new BusinessException("微信统一下单失败, 请稍后再试");
            }

            return WxCreateOrderResponse.create(response, wxPayConfig.getMchKey(), request.getPayTypeEnum());
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("【微信统一下单】, 发生异常 request:{},e: ", request, e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean verify(Map map, SignType signType, String sign) {
        return WxPaySignature.verify(map, wxPayConfig.getMchKey());
    }

    /**
     * 异步通知
     *
     * @param notifyData
     * @return
     */
    @Override
    public ThirdPayResultResponse asyncNotify(String notifyData) {
        //签名校验
        if (!WxPaySignature.verify(XmlUtil.toMap(notifyData), wxPayConfig.getMchKey())) {
            log.warn("【微信支付异步通知】校验失败, notifyData:{}", notifyData);
            throw new BusinessException("微信支付, 校验失败");
        }

        //xml解析为对象
        WxPayAsyncResponse asyncResponse = (WxPayAsyncResponse) XmlUtil.toObject(notifyData, WxPayAsyncResponse.class);

        assert asyncResponse != null;
        if (!asyncResponse.getReturnCode().equals(WxPayConstants.SUCCESS)) {
            log.warn("【微信支付异步通知】调用失败, notifyData:{} ,response:{}", notifyData, asyncResponse);
            throw new BusinessException("微信支付失败");
        }
        //该订单已支付直接返回
        if (!asyncResponse.getResultCode().equals(WxPayConstants.SUCCESS)
                && asyncResponse.getErrCode().equals("ORDERPAID")) {
            return ThirdPayResultResponse.create(asyncResponse);
        }

        if (!asyncResponse.getResultCode().equals(WxPayConstants.SUCCESS)) {
            log.warn("【微信支付异步通知】支付失败, notifyData:{} ,response:{}", notifyData, asyncResponse);
            throw new BusinessException("微信支付失败");
        }

        return ThirdPayResultResponse.create(asyncResponse);
    }

    /**
     * 微信退款
     *
     * @param request
     * @return
     */
    @Override
    public ThirdPayRefundResponse refund(ThirdPayRefundRequest request) {
        try {
            WxPayRefundRequest wxRequest = new WxPayRefundRequest();
            wxRequest.setOutTradeNo(request.getTradeNo());
            wxRequest.setOutRefundNo(request.getTradeNo());
            wxRequest.setTransactionId(request.getOutTradeNo());
            wxRequest.setTotalFee(MoneyUtil.Yuan2Fen(request.getOrderAmount()));
            wxRequest.setRefundFee(MoneyUtil.Yuan2Fen(request.getRefundAmount()));

            wxRequest.setAppid(wxPayConfig.getAppId());
            wxRequest.setMchId(wxPayConfig.getMchId());
            wxRequest.setNonceStr(RandomUtil.getRandomStr());
            wxRequest.setSign(WxPaySignature.sign(MapUtil.buildMap(wxRequest), wxPayConfig.getMchKey()));

            //初始化证书
            if (wxPayConfig.getSslContext() == null) {
                wxPayConfig.initSSLContext();
            }
            String xml = XmlUtil.toString(wxRequest);
            RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"), xml);
            Call<WxRefundResponse> call = getSslContextRetrofit().create(WxPayApi.class).refund(body);
            Response<WxRefundResponse> retrofitResponse = call.execute();
            if (!retrofitResponse.isSuccessful()) {
                log.warn("【微信退款】调用失败, request:{}, response:{}", request, retrofitResponse);
                throw new BusinessException("微信退款失败, 网络异常");
            }
            WxRefundResponse response = retrofitResponse.body();

            assert response != null;
            if (!response.getReturnCode().equals(WxPayConstants.SUCCESS) || !response.getResultCode().equals(WxPayConstants.SUCCESS)) {
                log.warn("【微信退款】退款失败, request:{}, response:{}", request, response);
                if (!StringUtils.isEmpty(response.getReturnMsg())) {
                    throw new BusinessException(response.getReturnMsg());
                }
                throw new BusinessException("微信退款失败,请稍后再试");
            }

            return ThirdPayRefundResponse.create(response);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("【微信退款】, 发生异常 request:{},e: ", request, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询订单
     *
     * @param request
     * @return
     */
    @Override
    public ThirdPayQueryResponse query(ThirdPayQueryRequest request) {
        try {

            WxOrderQueryRequest wxRequest = new WxOrderQueryRequest();
            wxRequest.setOutTradeNo(request.getTradeNo());
            wxRequest.setTransactionId(request.getOutTradeNo());

            wxRequest.setAppid(wxPayConfig.getAppId());
            wxRequest.setMchId(wxPayConfig.getMchId());
            wxRequest.setNonceStr(RandomUtil.getRandomStr());
            wxRequest.setSign(WxPaySignature.sign(MapUtil.buildMap(wxRequest), wxPayConfig.getMchKey()));
            RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),
                    XmlUtil.toString(wxRequest));

            Call<WxOrderQueryResponse> call = getRetrofit().create(WxPayApi.class).orderquery(body);
            Response<WxOrderQueryResponse> retrofitResponse = call.execute();
            if (!retrofitResponse.isSuccessful()) {
                log.warn("【微信订单查询】, 调用失败, request:{} ,response:{}", request, retrofitResponse);
                return null;
            }
            WxOrderQueryResponse response = retrofitResponse.body();

            assert response != null;
            if (!response.getReturnCode().equals(WxPayConstants.SUCCESS) ||
                    !response.getResultCode().equals(WxPayConstants.SUCCESS)) {
                log.warn("【微信订单查询】, 查询失败, request:{} ,response:{}", request, response);
                return null;
            }

            String finishTime = null;
            if (response.getTimeEnd() != null) {
                finishTime =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.parse(response.getTimeEnd(), DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
            }
            return ThirdPayQueryResponse.builder()
                    .thirdPayOrderStatusEnum(ThirdPayOrderStatusEnum.findByName(response.getTradeState()))
                    .resultMsg(response.getTradeStateDesc())
                    .outTradeNo(response.getTransactionId())
                    .orderId(response.getOutTradeNo())
                    .attach(response.getAttach())
                    .finishTime(finishTime)
                    .build();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("【微信订单查询】, 发生异常 request:{},e: ", request, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * @param request
     * @return
     */
    @Override
    public String downloadBill(ThirdPayDownloadBillRequest request) {
        try {
            WxDownloadBillRequest wxRequest = new WxDownloadBillRequest();
            wxRequest.setBillDate(request.getBillDate());

            wxRequest.setAppid(wxPayConfig.getAppId());
            wxRequest.setMchId(wxPayConfig.getMchId());
            wxRequest.setNonceStr(RandomUtil.getRandomStr());
            wxRequest.setSign(WxPaySignature.sign(MapUtil.buildMap(wxRequest), wxPayConfig.getMchKey()));
            RequestBody body = RequestBody.create(MediaType.parse("application/xml; charset=utf-8"),
                    XmlUtil.toString(wxRequest));

            Call<ResponseBody> call = RETROFIT.create(WxPayApi.class).downloadBill(body);
            Response<ResponseBody> retrofitResponse = call.execute();

            if (!retrofitResponse.isSuccessful()) {
                log.warn("【微信对账单查询】, 调用失败 , request:{}, response:{}", request, retrofitResponse);
                throw new BusinessException("微信对账单查询, 网络异常");
            }

            assert retrofitResponse.body() != null;
            String response = retrofitResponse.body().string();

            //如果返回xml格式，表示返回异常
            if (response.startsWith("<")) {
                WxDownloadBillResponse downloadBillResponse = (WxDownloadBillResponse) XmlUtil.toObject(response,
                        WxDownloadBillResponse.class);
                assert downloadBillResponse != null;
                log.warn("【微信对账单查询】, 查询为空 , request:{}, response:{}", request, response);
                return null;
            }

            return response;
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("【微信对账单查询】, 发生异常 request:{},e: ", request, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据微信规则生成扫码二维码的URL
     *
     * @return
     */
    @Override
    public String getQrCodeUrl(String productId) {
        String appid = wxPayConfig.getAppId();
        String mch_id = wxPayConfig.getMchId();
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = RandomUtil.getRandomStr();

        //先构造要签名的map
        Map<String, String> map = new HashMap<>();
        map.put("appid", appid);
        map.put("mch_id", mch_id);
        map.put("product_id", productId);
        map.put("time_stamp", timeStamp);
        map.put("nonce_str", nonceStr);

        return "weixin://wxpay/bizpayurl?"
                + "appid=" + appid
                + "&mch_id=" + mch_id
                + "&product_id=" + productId
                + "&time_stamp=" + timeStamp
                + "&nonce_str=" + nonceStr
                + "&sign=" + WxPaySignature.sign(map, wxPayConfig.getMchKey());
    }

    @Override
    public ThirdPayTransferToAccountResponse transferToAccount(ThirdPayTransferToAccountRequest request) {
        throw new BusinessException("暂不支持微信");
    }

}
