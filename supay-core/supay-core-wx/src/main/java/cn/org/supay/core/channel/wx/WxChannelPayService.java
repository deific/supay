/*******************************************************************************
 * @(#)WxPayChannelService.java 2020年05月16日 09:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.org.supay.core.channel.BaseChannelPayService;
import cn.org.supay.core.channel.ChannelApiType;
import cn.org.supay.core.channel.notify.ChannelNotifyHandler;
import cn.org.supay.core.channel.wx.convert.WxPayConverter;
import cn.org.supay.core.channel.wx.data.*;
import cn.org.supay.core.channel.wx.filter.WxPayFilter;
import cn.org.supay.core.channel.wx.notify.WxPayNotifyData;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.utils.BeanUtils;
import cn.org.supay.core.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Map;

/**
 * <b>Application name：</b> WxChannelPayService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class WxChannelPayService implements BaseChannelPayService {

    @Override
    public SupayChannelType getSupportType() {
        return SupayChannelType.WECHAT;
    }

    @Override
    public void register() {
        // 注册渠道参数转换器，默认为JSON格式
        SupayCoreConfig.registerChannelApiParamConverter(SupayChannelType.WECHAT, new WxPayConverter());
        SupayCoreConfig.registerPayChannelService(getSupportType(), this, new WxPayFilter());
    }

    /**
     * 获取接口请求的 URL
     *
     * @param wxApiType {@link WxApiType} 支付 API 接口枚举
     * @return {@link String} 返回完整的接口请求URL
     */
    @Override
    public String getReqUrl(SupayChannelConfig config, ChannelApiType wxApiType, Boolean isSandBox) {
        boolean useSandBox = config.isSandBox();
        useSandBox = useSandBox || isSandBox;
        return config.getApiBaseUrl().concat(useSandBox? WxApiType.SAND_BOX_URL.getUrl() + wxApiType.getUrl():wxApiType.getUrl());
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        WxApiType apiType = ctx.getRequest().getPayType().equals(SupayPayType.WX_MICRO_PAY)?WxApiType.MICRO_ORDER:WxApiType.UNIFIED_ORDER;
        return callApi(ctx, WxPayUnifiedOrderRequest.class, WxPayUnifiedOrderResponse.class, apiType);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        return callApi(ctx, WxPayRefundRequest.class, WxPayRefundResponse.class, WxApiType.REFUND);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryPay(SupayContext<? extends Request, ? extends Response> ctx) {
        return callApi(ctx, WxPayOrderQueryRequest.class, WxPayOrderQueryResponse.class, WxApiType.PAY_QUERY);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryRefund(SupayContext<? extends Request, ? extends Response> ctx) {
        return callApi(ctx, WxPayRefundQueryRequest.class, WxPayRefundQueryResponse.class, WxApiType.REFUND_QUERY);
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
                                                                        WxApiType apiType) {
        // 设置随机数和签名
        SupayChannelConfig channelConfig = ctx.getChannelConfig();
        WxPayBaseRequest request = ctx.getRequest(requestClass);
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
        String resXml = null;
        if (apiType.isSslCertRequired()) {
            resXml = HttpUtils.postSsl(channelConfig.getSslSocketFactory(), targetUrl, reqXml);
        } else {
            resXml = HttpUtils.post(targetUrl, reqXml);
        }
        log.debug("[微信渠道] 接口：{} 耗时：{}ms 请求响应：{}", targetUrl, System.currentTimeMillis() - startCallTime, resXml);

        ctx.parseAndSetResponse(resXml, responseClass);
        return ctx;
    }

    @Override
    public String asyncNotifyCallback(Map formParam, InputStream body) {
        // 参数校验
        String appId = (String) formParam.get("app_id");
        try {
            // 解析流数据
            WxPayNotifyData notifyData = null;
            String bodyStr = null;
            if (body != null) {
                bodyStr = new String(IoUtil.readBytes(body));
                notifyData = BeanUtils.xmlToBean(bodyStr, WxPayNotifyData.class);
            }
            // 校验
            if (notifyData != null && !notifyData.checkSign(SupayCoreConfig.getChannelConfig(notifyData.getAppid()))) {
                log.error("异步回调验签失败：{}", bodyStr);
                return "验签失败";
            }

            ChannelNotifyHandler callbackHandler = SupayCoreConfig.getNotifyHandler(getSupportType());
            if (callbackHandler != null) {
                return callbackHandler.handle(notifyData, this);
            }
        } catch (Exception e) {
            log.error("异步回调验证失败：", e);
            return "验证异常";
        }
        return "不支持该通知";
    }
}