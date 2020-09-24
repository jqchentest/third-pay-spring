package com.jon.thirdpay.model.wxpay;

import com.jon.thirdpay.model.wxpay.response.WxOrderQueryResponse;
import com.jon.thirdpay.model.wxpay.response.WxRefundResponse;
import com.jon.thirdpay.model.wxpay.response.WxUnifiedOrderResponse;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 微信支付
 */
public interface WxPayApi {

    /**
     * 统一下单
     *
     * @param body
     * @return
     */
    @POST("/pay/unifiedorder")
    Call<WxUnifiedOrderResponse> unifiedorder(@Body RequestBody body);

    /**
     * 申请退款
     *
     * @param body
     * @return
     */
    @POST("/secapi/pay/refund")
    Call<WxRefundResponse> refund(@Body RequestBody body);

    /**
     * 订单查询
     *
     * @param body
     * @return
     */
    @POST("/pay/orderquery")
    Call<WxOrderQueryResponse> orderquery(@Body RequestBody body);

    @POST("/pay/downloadbill")
    Call<ResponseBody> downloadBill(@Body RequestBody body);
}
