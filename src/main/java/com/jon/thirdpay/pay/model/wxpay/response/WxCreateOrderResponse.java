package com.jon.thirdpay.pay.model.wxpay.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jon.thirdpay.pay.enums.ThirdPayTypeEnum;
import com.jon.thirdpay.pay.service.impl.wxpay.WxPaySignature;
import com.jon.thirdpay.pay.utils.RandomUtil;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信统一下单返回结果
 *
 * @author testjon 2021-07-14
 */
@Data
public class WxCreateOrderResponse {

    /**
     * 应用ID
     */
    private String appId;
    /**
     * 商户号
     */
    private String partnerId;
    /**
     * 微信返回的支付交易会话ID
     */
    private String prepayId;
    /**
     * 扩展字段, 暂填写固定值Sign=WXPay
     */
    @JsonProperty("package")
    private String packAge;
    /**
     * 随机字符串
     */
    private String nonceStr;
    /**
     * 时间戳
     */
    private String timeStamp;
    /**
     * 签名
     */
    private String sign;

    /**
     * 支付跳转链接
     * mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。
     */
    private String mWebUrl;

    /**
     * 返回微信统一下单返回结果参数；
     * > 根据参数再次签名生成签名
     */
    public static WxCreateOrderResponse create(WxUnifiedOrderResponse response,
                                               String mchKey,
                                               ThirdPayTypeEnum payTypeEnum) {
        if (ThirdPayTypeEnum.WXPAY_MINI.equals(payTypeEnum) || ThirdPayTypeEnum.WXPAY_MP.equals(payTypeEnum)) {
            return getJsApiPayResponse(response, mchKey);
        }
        if (ThirdPayTypeEnum.WXPAY_H5.equals(payTypeEnum)) {
            return getH5PayResponse(response, mchKey);
        }
        return getAppPayResponse(response, mchKey);
    }

    /**
     * APP再次加签后返回结果
     */
    private static WxCreateOrderResponse getAppPayResponse(WxUnifiedOrderResponse response, String mchKey) {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = RandomUtil.getRandomStr();
        String prepayId = response.getPrepayId();
        String packAge = "Sign=WXPay";

        //先构造要签名的map
        Map<String, String> map = new HashMap<>();
        map.put("appid", response.getAppid());
        map.put("partnerid", response.getMchId());
        map.put("prepayid", prepayId);
        map.put("package", packAge);
        map.put("noncestr", nonceStr);
        map.put("timestamp", timeStamp);

        //返回的内容
        WxCreateOrderResponse payResponse = new WxCreateOrderResponse();
        payResponse.setAppId(response.getAppid());
        payResponse.setPartnerId(response.getMchId());
        payResponse.setPrepayId(prepayId);
        payResponse.setPackAge(packAge);
        payResponse.setNonceStr(nonceStr);
        payResponse.setTimeStamp(timeStamp);
        payResponse.setSign(WxPaySignature.sign(map, mchKey));
        return payResponse;
    }

    /**
     * 小程序再次加签后返回结果
     */
    private static WxCreateOrderResponse getJsApiPayResponse(WxUnifiedOrderResponse response, String mchKey) {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        String nonceStr = RandomUtil.getRandomStr();
        String prepayId = response.getPrepayId();
        String packAge = "prepay_id=" + prepayId;

        //先构造要签名的map
        Map<String, String> map = new HashMap<>();
        map.put("appId", response.getAppid());
        map.put("nonceStr", nonceStr);
        map.put("package", packAge);
        map.put("signType", "MD5");
        map.put("timeStamp", timeStamp);

        //返回的内容
        WxCreateOrderResponse payResponse = new WxCreateOrderResponse();
        payResponse.setAppId(response.getAppid());
        payResponse.setPartnerId(response.getMchId());
        payResponse.setPrepayId(prepayId);
        payResponse.setPackAge(packAge);
        payResponse.setNonceStr(nonceStr);
        payResponse.setTimeStamp(timeStamp);
        payResponse.setSign(WxPaySignature.sign(map, mchKey));
        return payResponse;
    }

    /**
     * H5支付返回结果
     */
    private static WxCreateOrderResponse getH5PayResponse(WxUnifiedOrderResponse response, String mchKey) {
        String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);

        //返回的内容
        WxCreateOrderResponse payResponse = new WxCreateOrderResponse();
        payResponse.setAppId(response.getAppid());
        payResponse.setPartnerId(response.getMchId());
        payResponse.setPrepayId(response.getPrepayId());
        payResponse.setNonceStr(response.getNonceStr());
        payResponse.setTimeStamp(timeStamp);
        payResponse.setMWebUrl(response.getMwebUrl());
        payResponse.setSign(response.getSign());
        return payResponse;
    }
}
