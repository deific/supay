# supay
简单（super）支付系统
简单轻量级可扩展的支付系统。提供了优雅架构设计的支付工具包和完善的支付系统。
工具包可单独使用，通过整合、抽象、聚合第三方支付系统接口，对外提供简洁的使用和调用接口完成第三方支付接口（微信,支付宝,银联等等）调用，实现了扫码支付、H5支付、小程序支付等，支付与业务完全剥离，简单几行代码即可实现支付，简单快速完成支付模块的开发，可轻松嵌入到任何系统里。并且采用灵活的扩展性架构，以便实现更多的第三方支付接口。
支付系统完善的支付系统。包括商户管理，用户账户体系，支付管理，支付路由，订单管理，上下游对账，清结算等支付核心功能，目标是打造一个开源的完善的可扩展的用于生产的支付系统。

## 特性
```java
1. 极少依赖，轻量级可扩展的工具包（SDK)，可嵌入任何系统
2. 统一支付入口，简化调用,只使用SupayCore类即可
3. 高性能异步线程处理请求和异步通知
4. 支持多商户多应用
5. 支持调用过滤器机制，可以灵活扩展调用逻辑,第三方接口灵活切换使用55
6. 支付Context设计，统一封装请求响应参数，可以灵活控制每一笔支付交易

```

## 示例
```java
    // 初始化渠道配置
    SupayChannelConfig channelConfig = SupayChannelConfig.builder()
            .appId("appId").appSecret("appSecret").appName("微信公众号-测试")
            .mchId("123").mchSecretKey("cccccccccc").mchName("测试")
            .channelType(SupayChannelType.WECHAT).apiBaseUrl(WxPayApiTypePay.BASE_URL_CHINA1.getUrl())
            .build().register();
    // 注册渠道服务实现
    SupayConfig.registerPayService(SupayChannelType.WECHAT, new WxPayChannelService());
    // 注册渠道参数转换器，默认为JSON格式
    SupayConfig.registerParamConverter(SupayChannelType.WECHAT, new WxPayConverter());
    // 注册调用过滤器，非必须
    WxPayFilter wxPayFilter = new WxPayFilter();


    String orderCode = IdUtil.fastSimpleUUID();
    // 微信支付

    // 构建支付上下文
    // 构建支付上下文参数
    WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.builder()
            .body("测试微信支付订单")
            .outTradeNo(orderCode)
            .productId("12")
            .notifyUrl("https://www.spay.org.cn/notify")
            .totalFee("100")
            .timeStart(DateUtil.format(new Date(), "yyyyMMddHHmmss"))
            .timeExpire(DateUtil.format(DateUtil.offsetMinute(new Date(), 15), "yyyyMMddHHmmss"))
            .tradeType(SupayPayType.WX_MP_PAY.getCode())
            .openid("aaaaaaaaaaaaaaaaaaaaa")
            .spbillCreateIp("127.0.0.1")
            .nonceStr(String.valueOf(System.currentTimeMillis()))
            .build();

    // 构建微信支付上下文
    SupayContext cxt = SupayContext.buildContext(channelConfig, request, false, wxPayFilter);
    // 调用支付接口
    cxt = (SupayContext) SupayCore.pay(cxt);
    log.debug("交易状态：{} 信息：{} 耗时：{} 接口响应数据：{}", cxt.hasError(), cxt.getMsg(), cxt.duration(), cxt.getResponse());

    // 查询支付订单
    WxPayOrderQueryRequest qReq = WxPayOrderQueryRequest.builder().outTradeNo(orderCode).build();
    SupayContext qCtx = SupayContext.builder()
            .channelConfig(SupayConfig.getPayConfig("wxf4a7649a7bf71c11"))
            .request(qReq)
            .build();

    SupayCore.queryPayOrder(qCtx);

    log.debug("查询结果：{}", qCtx.getResponse());


```