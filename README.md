# 第三方支付接入demo
微信支付(开发文档V2版)、支付宝支付常用接口，spring版; 配置即可用, 无需修改  
```
com.jon.thirdpay.pay: 只包含第三方支付相关代码(目的是为了方便集成和剥离，无需关注业务)  
com.jon.thirdpay.business: 包含第三方支付回调业务处理相关demo
```

最初基于 _https://github.com/Pay-Group/best-pay-sdk_  此开源项目做了修改

## 支持接口
#### 支付宝(支持普通公钥/证书方式)
    1 发起支付
    2 验证支付结果
    3 异步回调通知
    4 退款
    5 查询订单
    6 下载对账单
    7 转账到支付宝账户
    

#### 微信(开发文档V2版)（支持APP/JSAPI/小程序/公众号/H5）
    1 发起支付
    2 验证支付结果
    3 异步回调通知
    4 退款
    5 查询订单
    6 下载对账单
    7 生成二维码URL



