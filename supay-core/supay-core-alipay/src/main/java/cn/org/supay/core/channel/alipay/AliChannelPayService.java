/*******************************************************************************
 * @(#)AliPayChannelService.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay;

import cn.org.supay.core.channel.BaseChannelPayService;
import cn.org.supay.core.channel.alipay.data.*;
import cn.org.supay.core.channel.alipay.filter.AliPayFilter;
import cn.org.supay.core.channel.alipay.notify.AliPayNotifyData;
import cn.org.supay.core.channel.alipay.sdk.Factory;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.channel.notify.ChannelNotifyHandler;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.context.SupayNotifyContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.utils.BeanUtils;
import com.alibaba.fastjson.JSON;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.alipay.easysdk.payment.wap.models.AlipayTradeWapPayResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * <b>Application name：</b> AliChannelPayService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月25日 19:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class AliChannelPayService implements BaseChannelPayService {

    @Override
    public SupayChannelType getSupportType() {
        return SupayChannelType.ALIPAY;
    }

    @Override
    public void register() {
        SupayCoreConfig.registerPayChannelService(getSupportType(), this,
                new AliPayFilter());
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> thisCtx) {
        try {
            // 简易支付
            SupayPayType payType = thisCtx.getRequest().getPayType();
            switch (payType) {
                case ALI_PAGE_PAY:
                    AliPayPageRequest request = thisCtx.getRequest(AliPayPageRequest.class);
                    AlipayTradePagePayResponse response = Factory.Payment.Page(thisCtx.getChannelConfig().getAppId()).batchOptional(request.getOptionParams())
                            .pay(request.getSubject(), request.getOutTradeNo(), request.getTotalAmount(), request.getReturnUrl());
                    AliPayPageResponse pageResponse = new AliPayPageResponse();
                    pageResponse.setBody(response.body);
                    pageResponse.setSuccess(true);
                    pageResponse.setCode(0);
                    pageResponse.setMsg("成功");
                    thisCtx.setResponse(pageResponse);
                    return thisCtx;
                case ALI_WAP_PAY:
                    AliPayPageRequest wapRequest = thisCtx.getRequest(AliPayPageRequest.class);
                    AlipayTradeWapPayResponse wapResponse = Factory.Payment.Wap(thisCtx.getChannelConfig().getAppId()).batchOptional(wapRequest.getOptionParams())
                            .pay(wapRequest.getSubject(), wapRequest.getOutTradeNo(),
                            wapRequest.getTotalAmount(), wapRequest.getReturnUrl(), wapRequest.getReturnUrl());
                    AliPayPageResponse wapPayResponse = new AliPayPageResponse();
                    wapPayResponse.setBody(wapResponse.body);
                    wapPayResponse.setSuccess(true);
                    wapPayResponse.setCode(0);
                    wapPayResponse.setMsg("成功");
                    thisCtx.setResponse(wapPayResponse);
                    return thisCtx;
                case ALI_APP_PAY:
                    AliPayAppRequest appRequest = thisCtx.getRequest(AliPayAppRequest.class);
                    AlipayTradeAppPayResponse appResponse = Factory.Payment.App(thisCtx.getChannelConfig().getAppId()).batchOptional(appRequest.getOptionParams())
                            .pay(appRequest.getSubject(), appRequest.getOutTradeNo(), appRequest.getTotalAmount());
                    AliPayAppResponse appPayResponse = new AliPayAppResponse();
                    appPayResponse.setBody(appResponse.body);
                    appPayResponse.setSuccess(true);
                    appPayResponse.setCode(0);
                    appPayResponse.setMsg("成功");
                    thisCtx.setResponse(appPayResponse);
                    return thisCtx;
                case ALI_FACE_PAY:
                    AliPayFaceRequest faceRequest = thisCtx.getRequest(AliPayFaceRequest.class);
                    AlipayTradePayResponse faceResponse = Factory.Payment.FaceToFace(thisCtx.getChannelConfig().getAppId()).batchOptional(faceRequest.getOptionParams())
                            .pay(faceRequest.getSubject(), faceRequest.getOutTradeNo(), faceRequest.getTotalAmount(), faceRequest.getAuthCode());
                    AliPayFaceResponse facePayResponse = AliPayFaceResponse.build(faceResponse.toMap());
                    thisCtx.setResponse(facePayResponse);
                    return thisCtx;
                case ALI_COMMON_PAY:
                    AliPayCommonRequest commonRequest = thisCtx.getRequest(AliPayCommonRequest.class);
                    AlipayTradeCreateResponse commonResponse = Factory.Payment.Common(thisCtx.getChannelConfig().getAppId()).batchOptional(commonRequest.getOptionParams())
                            .create(commonRequest.getSubject(), commonRequest.getOutTradeNo(), commonRequest.getTotalAmount(), commonRequest.getBuyerId());
                    AliPayCommonResponse commonPayResponse = AliPayCommonResponse.build(commonResponse.toMap());

                    thisCtx.setResponse(commonPayResponse);
                    return thisCtx;
            }
        } catch (Exception e) {
            thisCtx.fail("调用阿里支付接口异常:" + e.getMessage());
            log.error("调用阿里支付接口异常",e);
        }
        return thisCtx;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        // 检查并转换类型
        SupayContext<AliPayRefundRequest, AliPayRefundResponse> thisCtx = ctx.checkAndConvertType(AliPayRefundRequest.class, AliPayRefundResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }
        AliPayRefundRequest refundRequest = thisCtx.getRequest(AliPayRefundRequest.class);
        try {
            AlipayTradeRefundResponse refundResponse = Factory.Payment.Common(ctx.getChannelConfig().getAppId())
                    .refund(refundRequest.getOutTradeNo(), refundRequest.getRefundAmount());
            AliPayRefundResponse response = AliPayRefundResponse.build(refundResponse.toMap());
            thisCtx.setResponse(response);
        } catch (Exception e) {
            log.error("调用阿里退款接口异常：", e);
            thisCtx.fail("调用阿里退款接口异常:" + e.getMessage());
        }
        return thisCtx;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryPay(SupayContext<? extends Request, ? extends Response> ctx) {
        // 检查并转换类型
        SupayContext<AliPayQueryRequest, AliPayQueryResponse> thisCtx = ctx.checkAndConvertType(AliPayQueryRequest.class, AliPayQueryResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }
        long startTime = System.currentTimeMillis();
        AliPayQueryRequest queryRequest = thisCtx.getRequest(AliPayQueryRequest.class);
        try {
            log.debug("[支付宝]查询交易请求：{}", JSON.toJSONString(queryRequest));
            AlipayTradeQueryResponse queryResponse = Factory.Payment.Common(ctx.getChannelConfig().getAppId()).query(queryRequest.getOutTradeNo());
            log.debug("[支付宝]查询交易响应：耗时：{}ms, 结果：{}", System.currentTimeMillis() - startTime, JSON.toJSONString(queryResponse));
            AliPayQueryResponse response = AliPayQueryResponse.build(queryResponse.toMap());
            thisCtx.setResponse(response);
        } catch (Exception e) {
            log.error("调用阿里支付查询接口异常：", e);
            thisCtx.fail("调用阿里支付查询接口异常:" + e.getMessage());
        }
        return thisCtx;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryRefund(SupayContext<? extends Request, ? extends Response> ctx) {
        // 检查并转换类型
        SupayContext<AliPayRefundQueryRequest, AliPayRefundQueryResponse> thisCtx = ctx.checkAndConvertType(AliPayRefundQueryRequest.class, AliPayRefundQueryResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }
        AliPayRefundQueryRequest refundQueryRequest = thisCtx.getRequest(AliPayRefundQueryRequest.class);
        try {
            AlipayTradeFastpayRefundQueryResponse queryResponse = Factory.Payment.Common(ctx.getChannelConfig().getAppId())
                    .queryRefund(refundQueryRequest.getOutTradeNo(), refundQueryRequest.getOutRefundNo());
            AliPayQueryResponse response = AliPayQueryResponse.build(queryResponse.toMap());
            thisCtx.setResponse(response);
        } catch (Exception e) {
            log.error("调用阿里支付查询接口异常：", e);
            thisCtx.fail("调用阿里支付查询接口异常:" + e.getMessage());
        }
        return thisCtx;
    }

    @Override
    public SupayNotifyContext checkAndHandleCallbackNotify(SupayNotifyContext notifyContext, ChannelNotifyHandler handler) {
        // 参数校验
        String appId = (String) notifyContext.getFormParam().get("app_id");
        try {
            boolean isOk = Factory.Payment.Common(appId).verifyNotify(notifyContext.getFormParam());
            if (!isOk) {
                return notifyContext.result("验证失败");
            }

            // 解析参数
            AliPayNotifyData notifyData = new AliPayNotifyData() {
                @Override
                public Map getNotifyOriginData() {
                    return notifyContext.getFormParam();
                }
            };

            // 填充参数
            BeanUtils.fillBeanWithMap(notifyContext.getFormParam(), notifyData, true);

            ChannelNotifyHandler callbackHandler = handler;
            if (callbackHandler == null) {
                SupayCoreConfig.getNotifyHandler(getSupportType());
            }

            boolean notifyOk = false;
            if (callbackHandler != null) {
                notifyOk = callbackHandler.handleNotify(notifyContext.getNotifyType(), notifyData);
            }
            return notifyContext.result(notifyOk?"成功":"失败");
        } catch (Exception e) {
            log.error("异步回调验证失败：", e);
            return notifyContext.result("验证异常");
        }
    }
}