package com.jon.thirdpay.web;

import com.jon.thirdpay.enums.ThirdPayTypeEnum;
import com.jon.thirdpay.service.PayCallbackService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author testjon 2020/8/10.
 */
@RestController
@Slf4j
public class PayCallbackController {
    @Autowired
    private PayCallbackService payCallbackService;

    /**
     * 微信支付回调接口
     */
    @RequestMapping(value = "wx/payCallBack", method = RequestMethod.POST)
    public void wxPayCallBack(HttpServletRequest request, HttpServletResponse response) {
        try (ServletInputStream inputStream = request.getInputStream();
             ServletOutputStream outputStream = response.getOutputStream()) {
            String notifyData = IOUtils.toString(inputStream);
            log.info(">>>>>>>>> 【微信支付回调】>>>>>>>>>>> notifyData:{}", notifyData);

            if (StringUtils.isEmpty(notifyData)) {
                log.warn(">>>>>>>>> 【微信支付回调】回调内容为空 request: {} ", request);
                return;
            }
            payCallbackService.payCallback(notifyData, ThirdPayTypeEnum.WXPAY_APP);
            log.info(">>>>>>>>>>【微信支付回调】<<<<<<<<<<<<<<<  success ");
            String sb = "<xml>\n" +
                    "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                    "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                    "</xml>";
            outputStream.println(sb);
        } catch (Exception e) {
            log.error(">>>>>>>>> 【微信支付回调】发生异常 request: {}, e: ", request, e);
        }
    }

    /**
     * 支付宝支付回调接口
     */
    @RequestMapping(value = "aliPay/payCallBack", method = RequestMethod.POST)
    public String aliPayCallBack(HttpServletRequest request, HttpServletResponse response) {
        try (ServletInputStream inputStream = request.getInputStream();
             ServletOutputStream outputStream = response.getOutputStream()) {
            String notifyData = IOUtils.toString(inputStream);

            log.info(">>>>>>>>> 【支付宝支付回调】>>>>>>>>>>> notifyData:{}", notifyData);
            if (StringUtils.isEmpty(notifyData)) {
                log.warn(">>>>>>>>> 【支付宝支付回调】回调内容为空 request: {} ", request);
                return "fail";
            }
            payCallbackService.payCallback(notifyData, ThirdPayTypeEnum.ALIPAY_APP);
            log.info(">>>>>>>>>>【支付宝支付回调】<<<<<<<<<<<<<<<  success ");

            return "success";
        } catch (Exception e) {
            log.error(">>>>>>>>> 【支付宝支付回调】发生异常 request: {}, e: ", request, e);
        }
        return "fail";
    }

}
