/*******************************************************************************
 * @(#)WxPayChannelService.java 2020年05月16日 09:37
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.spay.core.channel.BasePayChannelService;
import com.spay.core.channel.wx.data.*;
import com.spay.core.config.SpayChannelConfig;
import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import com.spay.core.utils.HttpUtils;
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
        return config.getApiBaseUrl().concat(config.isSandBox()?WxPayApiType.SAND_BOX_URL.getUrl():wxApiType.getUrl());
    }

    @Override
    public SpayContext<? extends Request, ? extends Response> pay(SpayContext<? extends Request, ? extends Response> ctx) {
        return callApi(ctx, WxPayOrderQueryRequest.class, WxPayBaseResponse.class, WxPayApiType.UNIFIED_ORDER);
    }

    @Override
    public SpayContext<? extends Request, ? extends Response> queryTradeInfo(SpayContext<? extends Request, ? extends Response> ctx) {
        return callApi(ctx, WxPayOrderQueryRequest.class, WxPayBaseResponse.class, WxPayApiType.PAY_QUERY);
    }

    /**
     * 调用微信接口
     * @param ctx
     * @param requestClass
     * @param responseClass
     * @param apiType
     * @return
     */
    private SpayContext<? extends Request, ? extends Response> callApi(SpayContext<? extends Request, ? extends Response> ctx,
                                                                       Class<? extends WxPayBaseRequest> requestClass, Class<? extends WxPayBaseResponse> responseClass, WxPayApiType apiType) {
        // 检查并转换类型
        SpayContext<WxPayBaseRequest, WxPayBaseResponse> thisCtx = checkAndConvertType(ctx,
                requestClass, responseClass);
        if (ctx.hasError()) {
            return ctx;
        }
        // 设置随机数和签名
        SpayChannelConfig channelConfig = ctx.getChannelConfig();
        WxPayBaseRequest request = thisCtx.getRequest();
        request.setAppid(StrUtil.isNotEmpty(request.getAppid())?request.getAppid():channelConfig.getAppId());
        request.setMchId(StrUtil.isNotEmpty(request.getMchId())?request.getMchId():channelConfig.getMchId());
        request.setNonceStr(RandomUtil.randomString(16));
        // 参数检查并签名
        request.checkAndSign(ctx);
        if (ctx.hasError()) {
            return ctx;
        }
        String reqXml = ctx.toRequestStr();

        log.debug("[微信渠道] 接口：{} 请求参数：{}", apiType.getUrl(), reqXml);
        long startCallTime = System.currentTimeMillis();
        String resXml = HttpUtils.post(getReqUrl(ctx.getChannelConfig(), apiType), reqXml);
        log.debug("[微信渠道] 接口：{} 耗时：{} 请求响应：{}", apiType.getUrl(), System.currentTimeMillis() - startCallTime, resXml);
        ctx.parseResponseStr(resXml, responseClass);
        return ctx;
    }
}