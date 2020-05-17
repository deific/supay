/*******************************************************************************
 * @(#)SpayCoreTest.java 2020年05月16日 09:07
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.test;

import com.spay.core.channel.wx.WxPayChannelService;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderRequest;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import com.spay.core.config.SpayChannelConfig;
import com.spay.core.config.SpayConfig;
import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import com.spay.core.enums.SpayChannelType;
import com.spay.core.pay.SpayCore;
import lombok.extern.slf4j.Slf4j;

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
public class WxSpayCoreTest {

    public static void main(String[] args) {

        SpayChannelConfig channelConfig = SpayChannelConfig.builder().appId("1").channelType(SpayChannelType.WECHAT).build();
        SpayConfig.registerPayConfig("1", channelConfig);
        SpayConfig.registerPayService(SpayChannelType.WECHAT, new WxPayChannelService());

        SpayContext<WxPayUnifiedOrderRequest, WxPayUnifiedOrderResponse> cxt = SpayContext.<WxPayUnifiedOrderRequest, WxPayUnifiedOrderResponse>builder()
                .channelConfig(SpayConfig.getPayConfig("1"))
                .request(WxPayUnifiedOrderRequest.builder().appid("a").build())
                .build();

        cxt = SpayCore.pay(cxt);
        System.out.printf("结果：" + cxt.getResponse());
    }
}