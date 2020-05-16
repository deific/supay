/*******************************************************************************
 * @(#)WxPayChannelService.java 2020年05月16日 09:37
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx;

import cn.hutool.http.HttpUtil;
import com.spay.core.channel.BasePayChannelService;
import com.spay.core.channel.PayChannelService;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderRequest;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import com.spay.core.context.SpayContext;
import com.spay.core.data.SpayRequest;
import com.spay.core.data.SpayResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Application name：</b> WxPayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class WxPayChannelService extends BasePayChannelService {

    private WxPayConverter convert = new WxPayConverter();

    @Override
    public String getPayServiceName() {
        return "wxPayChannelService";
    }

    @Override
    public SpayContext pay(SpayContext<? extends SpayRequest, ? extends SpayResponse> ctx) {

        SpayContext<WxPayUnifiedOrderRequest, WxPayUnifiedOrderResponse> thisCtx = checkType(ctx, WxPayUnifiedOrderRequest.class, WxPayUnifiedOrderResponse.class);
        if (ctx.hasError()) {
            return ctx;
        }

        String reqXml = convert.convert(ctx.getRequest());
        log.debug("[微信支付] 请求参数：{}", reqXml);
        String resXml = HttpUtil.post("http://www.baidu.com", reqXml);
        resXml = "<appid>aa</appid>";
        log.debug("[微信支付] 请求响应：{}", resXml);
        WxPayUnifiedOrderResponse response = (WxPayUnifiedOrderResponse)convert.convert(resXml, WxPayUnifiedOrderResponse.class);
        thisCtx.setResponse(response);
        return ctx;
    }

    @Override
    public SpayContext refund(SpayContext<? extends SpayRequest, ? extends SpayResponse> spayContext) {
        return null;
    }

    @Override
    public SpayResponse queryTradeInfo(SpayContext<? extends SpayRequest, ? extends SpayResponse> spayContext) {
        return null;
    }
}