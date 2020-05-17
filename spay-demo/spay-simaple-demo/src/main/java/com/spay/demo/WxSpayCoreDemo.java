/*******************************************************************************
 * @(#)SpayCoreTest.java 2020年05月16日 09:07
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.spay.core.channel.wx.WxPayApiType;
import com.spay.core.channel.wx.WxPayChannelService;
import com.spay.core.channel.wx.convert.WxPayConverter;
import com.spay.core.channel.wx.data.WxAppPayData;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderRequest;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import com.spay.core.config.SpayChannelConfig;
import com.spay.core.config.SpayConfig;
import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import com.spay.core.enums.SpayChannelType;
import com.spay.core.enums.SpayPayType;
import com.spay.core.enums.SpayTradeType;
import com.spay.core.pay.SpayCore;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * <b>Application name：</b> SpayCoreTest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class WxSpayCoreDemo {
    static {
        log.debug("aaaaaa");
    }

    public static void main(String[] args) {
        // 初始化配置
        SpayChannelConfig channelConfig = SpayChannelConfig.builder()
                .appId("wxf4a7649a7bf71c11").appSecret("95e56ee77c5a276c7e348dda3298b118").appName("微信公众号-医德帮-支付")
                .mchId("1332506201").mchSecretKey("0MOPDIPP79M2A9DKM4IB08IFPQ5MLDCW").mchName("明医众禾微信商户")
                .channelType(SpayChannelType.WECHAT).apiBaseUrl(WxPayApiType.BASE_URL_CHINA1.getUrl())
                .build().register();


        SpayConfig.registerPayService(SpayChannelType.WECHAT, new WxPayChannelService());
        SpayConfig.registerParamConverter(SpayChannelType.WECHAT, new WxPayConverter());

        // 构建支付上下文参数
        SpayContext<WxPayUnifiedOrderRequest, WxPayUnifiedOrderResponse<WxAppPayData>> cxt = SpayContext.<WxPayUnifiedOrderRequest, WxPayUnifiedOrderResponse<WxAppPayData>>builder()
                .channelConfig(SpayConfig.getPayConfig("wxf4a7649a7bf71c11"))
                .request(WxPayUnifiedOrderRequest.builder()
                        .body("测试微信支付订单")
                        .outTradeNo(UUID.fastUUID().toString(true))
                        .productId("12")
                        .notifyUrl("https://www.spay.com/notify")
                        .totalFee("100")
                        .timeStart(DateUtil.format(new Date(), "yyyyMMddHHmmss"))
                        .timeExpire(DateUtil.format(DateUtil.offsetMinute(new Date(), 15), "yyyyMMddHHmmss"))
                        .tradeType(SpayPayType.WX_MP_PAY.getCode())
                        .openid("ouQdUuJzQX4t-IuiGM71rnifMoRc")
                        .spbillCreateIp("127.0.0.1")
                        .nonceStr(String.valueOf(System.currentTimeMillis()))
                        .build())
                .build();

        cxt = SpayCore.pay(cxt);
        log.debug("交易状态：{} 信息：{} 接口响应数据：{}", cxt.hasError(), cxt.getMsg(), cxt.getResponse());
    }
}