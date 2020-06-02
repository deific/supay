/*******************************************************************************
 * @(#)AliPayChannelService.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay;

import cn.hutool.core.bean.BeanUtil;
import cn.org.supay.core.channel.BasePayChannelService;
import cn.org.supay.core.channel.alipay.data.*;
import cn.org.supay.core.channel.alipay.sdk.Factory;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;
import cn.org.supay.core.enums.SupayPayType;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeRefundResponse;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePayResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Application name：</b> AliPayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月25日 19:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class AliPayChannelService implements BasePayChannelService {

    @Override
    public String getPayServiceName() {
        return "aliPayChannelService";
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        // 检查并转换类型
        SupayContext<AliPayBaseRequest, AlipayBaseResponse> thisCtx = checkAndConvertType(ctx,
                AliPayBaseRequest.class, AlipayBaseResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }
        try {
            // 简易支付
            SupayPayType payType = thisCtx.getRequest().getPayType();
            switch (payType) {
                case ALI_PAGE_PAY:
                    AliPayPageRequest request = (AliPayPageRequest) thisCtx.getRequest();
                    AlipayTradePagePayResponse response = Factory.Payment.Page(ctx.getChannelConfig().getAppId())
                            .pay(request.getSubject(), request.getOutTradeNo(), request.getTotalAmount(), request.getReturnUrl());
                    AliPayPageResponse pageResponse = new AliPayPageResponse();
                    pageResponse.setBody(response.body);
                    thisCtx.setResponse(pageResponse);
                    return thisCtx;
                case ALI_APP_PAY:
                    AliPayAppRequest appRequest = (AliPayAppRequest)thisCtx.getRequest();
                    AlipayTradeAppPayResponse appResponse = Factory.Payment.App(ctx.getChannelConfig().getAppId())
                            .pay(appRequest.getSubject(), appRequest.getOutTradeNo(), appRequest.getTotalAmount());
                    AliPayAppResponse appPayResponse = new AliPayAppResponse();
                    appPayResponse.setBody(appResponse.body);
                    thisCtx.setResponse(appPayResponse);
                    return thisCtx;
                case ALI_FACE_PAY:
                    AliPayFaceRequest faceRequest = (AliPayFaceRequest) thisCtx.getRequest();
                    AlipayTradePayResponse faceResponse = Factory.Payment.FaceToFace(ctx.getChannelConfig().getAppId())
                            .pay(faceRequest.getSubject(), faceRequest.getOutTradeNo(), faceRequest.getTotalAmount(), faceRequest.getAuthCode());
                    AliPayFaceResponse facePayResponse = AliPayFaceResponse.build(faceResponse.toMap());
                    ctx.setResponse(facePayResponse);
                    return ctx;
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
        SupayContext<AliPayRefundRequest, AliPayRefundResponse> thisCtx = checkAndConvertType(ctx,
                AliPayRefundRequest.class, AliPayRefundResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }
        AliPayRefundRequest refundRequest = thisCtx.getRequest();
        try {
            AlipayTradeRefundResponse refundResponse = Factory.Payment.Common(ctx.getChannelConfig().getAppId()).refund(refundRequest.getOutTradeNo(), refundRequest.getOutTradeNo());
            AliPayRefundResponse response = AliPayRefundResponse.build(refundResponse.toMap());
            thisCtx.setResponse(response);
        } catch (Exception e) {
            log.error("调用阿里退款接口异常：", e);
            thisCtx.fail("调用阿里退款接口异常:" + e.getMessage());
        }
        return thisCtx;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryTradeInfo(SupayContext<? extends Request, ? extends Response> ctx) {
        // 检查并转换类型
        SupayContext<AliPayQueryRequest, AliPayQueryResponse> thisCtx = checkAndConvertType(ctx,
                AliPayQueryRequest.class, AliPayQueryResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }
        AliPayQueryRequest queryRequest = thisCtx.getRequest();
        try {
            AlipayTradeQueryResponse queryResponse = Factory.Payment.Common(ctx.getChannelConfig().getAppId()).query(queryRequest.getOutTradeNo());
            AliPayQueryResponse response = AliPayQueryResponse.build(queryResponse.toMap());
            thisCtx.setResponse(response);
        } catch (Exception e) {
            log.error("调用阿里支付查询接口异常：", e);
            thisCtx.fail("调用阿里支付查询接口异常:" + e.getMessage());
        }
        return thisCtx;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> sendRedPackage(SupayContext<? extends Request, ? extends Response> ctx) {
        // 检查并转换类型
        SupayContext<AliPayRefundQueryRequest, AliPayRefundQueryResponse> thisCtx = checkAndConvertType(ctx,
                AliPayRefundQueryRequest.class, AliPayRefundQueryResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }
        AliPayRefundQueryRequest refundQueryRequest = thisCtx.getRequest();
        try {
            AlipayTradeFastpayRefundQueryResponse queryResponse = Factory.Payment.Common(ctx.getChannelConfig().getAppId()).queryRefund(refundQueryRequest.getOutTradeNo(), refundQueryRequest.getOutRefundNo());
            AliPayRefundQueryResponse response = new AliPayRefundQueryResponse();
            BeanUtil.copyProperties(queryResponse, response);
            thisCtx.setResponse(response);
        } catch (Exception e) {
            log.error("调用阿里退款查询接口异常：", e);
            thisCtx.fail("调用阿里退款查询接口异常:" + e.getMessage());
        }
        return thisCtx;
    }
}