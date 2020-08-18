/*******************************************************************************
 * @(#)SupayAppPayResponse.java 2020年05月29日 12:26
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate;

import cn.org.supay.core.SupayCore;
import cn.org.supay.core.channel.aggregate.data.*;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * <b>Application name：</b> AliPayFilter.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年08月09日 16:22 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class Supay {


    /**
     * 扫码支付
     * 根据appId自动识别微信还是支付宝
     * @param appId 商户appId
     * @param tradeName 交易名称
     * @param outTradeNo 订单号
     * @param amount 交易金额
     * @param notifyUrl 通知地址
     * @return 二维码内容
     */
    public static String scanPay(String appId, String tradeName, String outTradeNo, BigDecimal amount, String notifyUrl) {

        SupayPayType payType = getMatchedPayType(appId, SupayPayType.WX_SCAN_PAY);
        if (payType == null) {
            log.error("当前商户appId对应的渠道不支持扫码扫码支付");
            return null;
        }

        // 构建支付上下文参数
        SupayContext cxt = SupayScanPayRequest.builder()
                .tradeNo(outTradeNo)
                .payType(payType)
                .tradeName(tradeName)
                .amount(amount)
                .payParam(SupayPayParamWxScan.builder().build())
                .notifyUrl(notifyUrl)
                .build()
                .toContext(appId, false);

        // 调用支付接口
        cxt = SupayCore.pay(cxt);

        SupayScanPayResponse scanPayResponse = ((SupayScanPayResponse)cxt.getResponse());

        String result = null;
        if (scanPayResponse.isSuccess()) {
            result = scanPayResponse.getQrCodeUrl();
        }
        return result;
    }


    /**
     * app支付
     * 根据商户appId自动识别渠道
     * @param appId
     * @param tradeName
     * @param outTradeNo
     * @param amount
     * @param notifyUrl
     * @return
     */
    public static String appPay(String appId, String tradeName, String outTradeNo, BigDecimal amount, String notifyUrl) {

        SupayPayType payType = getMatchedPayType(appId, SupayPayType.ALI_APP_PAY, SupayPayType.WX_APP_PAY);
        if (payType == null) {
            log.error("当前商户appId对应的渠道不支持扫码扫码支付");
            return null;
        }

        // 构建支付上下文参数
        SupayContext cxt = SupayAppPayRequest.builder()
                .tradeNo(outTradeNo)
                .payType(payType)
                .tradeName("测试APP支付")
                .amount(amount)
                .payParam(SupayPayParamWxApp.builder().appId(appId).build())
                .notifyUrl(notifyUrl)
                .build()
                .toContext(appId, false);

        // 调用支付接口
        cxt = SupayCore.pay(cxt);

        SupayAppPayResponse appPayResponse = ((SupayAppPayResponse)cxt.getResponse());

        String result = null;
        if (appPayResponse.isSuccess()) {
            result = appPayResponse.getAppPayBody();
        }
        return result;
    }

    /**
     * 退款接口
     * @param appId
     * @param originTradeNo
     * @param refundAmount
     * @param notifyUrl
     * @return
     */
    public static SupayRefundResponse refund(String appId, String originTradeNo, String refundTradeNo,
                                             BigDecimal totalAmount, BigDecimal refundAmount, String notifyUrl) {
        // 构建支付上下文参数
        SupayContext cxt = SupayRefundRequest.builder()
                .originTradeNo(originTradeNo)
                .refundTradeNo(refundTradeNo)
                .totalAmount(totalAmount)
                .refundAmount(refundAmount)
                .notifyUrl(notifyUrl)
                .build()
                .toContext(appId, false);

        // 调用支付接口
        cxt = SupayCore.refund(cxt);

        SupayRefundResponse refundResponse = ((SupayRefundResponse)cxt.getResponse());

        return refundResponse;
    }

    /**
     * 支付查询
     * @param appId
     * @param outTradeNo
     * @return
     */
    public static SupayPayQueryResponse payQuery(String appId, String outTradeNo) {
        // 构建支付上下文参数
        SupayContext cxt = SupayPayQueryRequest.builder()
                .outTradeNo(outTradeNo)
                .build()
                .toContext(appId, false);

        // 调用支付接口
        cxt = SupayCore.queryPayOrder(cxt);

        SupayPayQueryResponse payQueryResponse = ((SupayPayQueryResponse)cxt.getResponse());

        return payQueryResponse;
    }

    /**
     * 退款查询
     * @param appId
     * @param outTradeNo
     * @param refundNo
     * @return
     */
    public static SupayRefundQueryResponse refundQuery(String appId, String outTradeNo, String refundNo) {
        // 构建支付上下文参数
        SupayContext cxt = SupayRefundQueryRequest.builder()
                .outTradeNo(outTradeNo)
                .outRefundNo(refundNo)
                .build()
                .toContext(appId, false);

        // 调用支付接口
        cxt = SupayCore.queryPayOrder(cxt);

        SupayRefundQueryResponse refundQueryResponse = ((SupayRefundQueryResponse)cxt.getResponse());

        return refundQueryResponse;
    }

    /**
     * 查找该使用的支付方式
     * @param appId
     * @param payTypes
     * @return
     */
    private static SupayPayType getMatchedPayType(String appId, SupayPayType... payTypes) {
        SupayChannelConfig channelConfig = SupayCoreConfig.getChannelConfig(appId);
        SupayChannelType channelType = channelConfig.getChannelType();
        for (SupayPayType payType : payTypes) {
            if (payType.getChannel().equals(channelType)) {
                return payType;
            }
        }
        return null;
    }
}