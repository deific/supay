/*******************************************************************************
 * @(#)WxPayChannelService.java 2020年05月16日 09:37
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.spay.core.channel.BasePayChannelService;
import com.spay.core.channel.PayChannelService;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderRequest;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import com.spay.core.config.SpayChannelConfig;
import com.spay.core.context.SpayContext;
import com.spay.core.data.SpayRequest;
import com.spay.core.data.SpayResponse;
import com.spay.core.enums.SpayChannelType;
import com.spay.core.utils.BeanUtils;
import com.spay.core.utils.SignUtils;
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
public class WxPayChannelService implements BasePayChannelService {

    private WxPayConverter convert = new WxPayConverter();

    @Override
    public String getPayServiceName() {
        return "wxPayChannelService";
    }

    /**
     * 获取接口请求的 URL
     *
     * @param wxApiType {@link WxPayApiType} 支付 API 接口枚举
     * @return {@link String} 返回完整的接口请求URL
     */
    public static String getReqUrl(SpayChannelConfig config, WxPayApiType wxApiType) {
        return config.getApiBaseUrl().concat(config.isSandBox()?WxPayApiType.SAND_BOX_URL.getType():wxApiType.getType());
    }

    @Override
    public SpayContext pay(SpayContext<? extends SpayRequest, ? extends SpayResponse> ctx) {
        SpayContext<WxPayUnifiedOrderRequest, WxPayUnifiedOrderResponse> thisCtx = checkType(ctx,
                WxPayUnifiedOrderRequest.class, WxPayUnifiedOrderResponse.class);
        if (ctx.hasError()) {
            return ctx;
        }

        SpayChannelConfig channelConfig = ctx.getChannelConfig();
        // 设置随机数和签名
        WxPayUnifiedOrderRequest request = thisCtx.getRequest();
        request.setAppid(channelConfig.getAppId());
        request.setNonceStr(RandomUtil.randomString(16));
        String sign = SignUtils.signForMap(BeanUtils.xmlBean2Map(request), thisCtx.getChannelConfig().getMchSecretKey());
        request.setSign(sign);

        String reqXml = convert.convert(ctx.getRequest());
        log.debug("[微信支付] 请求参数：{}", reqXml);
        String resXml = HttpUtil.post(getReqUrl(ctx.getChannelConfig(), WxPayApiType.UNIFIED_ORDER), reqXml);
        log.debug("[微信支付] 请求响应：{}", resXml);
        WxPayUnifiedOrderResponse response = convert.convert(resXml, WxPayUnifiedOrderResponse.class);
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