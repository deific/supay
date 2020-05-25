/*******************************************************************************
 * @(#)WxPayChannelService.java 2020年05月16日 09:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.org.supay.core.channel.BasePayChannelService;
import cn.org.supay.core.channel.PayChannelApiType;
import cn.org.supay.core.channel.wx.data.WxPayBaseRequest;
import cn.org.supay.core.channel.wx.data.WxPayBaseResponse;
import cn.org.supay.core.channel.wx.data.WxPayOrderQueryRequest;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;
import cn.org.supay.core.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Application name：</b> WxPayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
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
    @Override
    public String getReqUrl(SupayChannelConfig config, PayChannelApiType wxApiType, Boolean isSandBox) {
        boolean useSandBox = config.isSandBox();
        useSandBox = useSandBox || isSandBox;
        return config.getApiBaseUrl().concat(useSandBox? WxPayApiType.SAND_BOX_URL.getUrl() + wxApiType.getUrl():wxApiType.getUrl());
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        return callApi(ctx, WxPayOrderQueryRequest.class, WxPayBaseResponse.class, WxPayApiType.UNIFIED_ORDER);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryTradeInfo(SupayContext<? extends Request, ? extends Response> ctx) {
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
    private SupayContext<? extends Request, ? extends Response> callApi(SupayContext<? extends Request, ? extends Response> ctx,
                                                                        Class<? extends WxPayBaseRequest> requestClass,
                                                                        Class<? extends WxPayBaseResponse> responseClass,
                                                                        WxPayApiType apiType) {
        // 检查并转换类型
        SupayContext<WxPayBaseRequest, WxPayBaseResponse> thisCtx = checkAndConvertType(ctx,
                requestClass, responseClass);
        if (ctx.hasError()) {
            return ctx;
        }
        // 设置随机数和签名
        SupayChannelConfig channelConfig = ctx.getChannelConfig();
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
        String targetUrl = getReqUrl(ctx.getChannelConfig(), apiType, ctx.isSandBox());

        log.debug("[微信渠道] 接口：{} 请求参数：{}", targetUrl, reqXml);
        long startCallTime = System.currentTimeMillis();
        String resXml = HttpUtils.post(targetUrl, reqXml);
        log.debug("[微信渠道] 接口：{} 耗时：{} 请求响应：{}", targetUrl, System.currentTimeMillis() - startCallTime, resXml);
        ctx.parseResponseStr(resXml, responseClass);
        return ctx;
    }
}