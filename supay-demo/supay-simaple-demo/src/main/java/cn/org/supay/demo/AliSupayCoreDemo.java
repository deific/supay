/*******************************************************************************
 * @(#)AliSupayCoreDemo.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.org.supay.core.channel.PayChannelService;
import cn.org.supay.core.channel.alipay.AliPayChannelService;
import cn.org.supay.core.channel.alipay.data.AliPayPageRequest;
import cn.org.supay.core.channel.alipay.data.AliPayPageResponse;
import cn.org.supay.core.channel.alipay.data.AliPayQueryRequest;
import cn.org.supay.core.channel.alipay.filter.AliPayFilter;
import cn.org.supay.core.channel.wx.WxPayApiType;
import cn.org.supay.core.channel.wx.WxPayChannelService;
import cn.org.supay.core.channel.wx.convert.WxPayConverter;
import cn.org.supay.core.channel.wx.data.WxPayOrderQueryRequest;
import cn.org.supay.core.channel.wx.data.WxPayUnifiedOrderRequest;
import cn.org.supay.core.channel.wx.filter.WxPayFilter;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.pay.SupayCore;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * <b>Application name：</b> AliSupayCoreDemo.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月30日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class AliSupayCoreDemo {

    private static Props props;
    private static  SupayChannelConfig channelConfig;

    // 初始化
    static {
        props = new Props("config/my-ali-pay.conf");
        // 初始化配置
        channelConfig = SupayChannelConfig.builder()
                .rootSecretKey(props.getStr("ali.publicKey"))
                .appId(props.getStr("ali.appId")).appSecret(props.getStr("ali.appSecret")).appName("支付宝应用-支付")
                .mchId(props.getStr("ali.mchId")).mchName("支付宝商户").mchSecretKey(props.getStr("ali.mchSecretKey"))
                .channelType(SupayChannelType.ALIPAY).apiBaseUrl("https://openapi.alipaydev.com/gateway.do")
                .build().register();

    }

    public static void main(String[] args) {
        // 注册渠道服务实现
        SupayConfig.registerPayService(SupayChannelType.ALIPAY, new AliPayChannelService());

        AliPayFilter filter = new AliPayFilter();

        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文
        // 构建支付上下文参数
        AliPayPageRequest request = AliPayPageRequest.builder()
                .outTradeNo(orderCode)
                .payType(SupayPayType.ALI_APP_PAY)
                .subject("测试网页支付")
                .totalAmount("1")
                .returnUrl("http://taobao.com")
                .build();

        // 构建微信支付上下文
        SupayContext cxt = SupayContext.buildContext(channelConfig, request, false, filter);
        // 调用支付接口
        cxt = (SupayContext) SupayCore.pay(cxt);
        log.debug("交易状态：{} 信息：{} 耗时：{} 接口响应数据：{}", cxt.hasError(), cxt.getMsg(), cxt.duration(), JSONUtil.toJsonStr(cxt.getResponse()));

        // 查询支付订单
        AliPayQueryRequest queryRequest = AliPayQueryRequest.builder().outTradeNo(orderCode).build();
        cxt = SupayContext.buildContext(channelConfig, queryRequest, false, filter);
        // 调用支付接口
        cxt = (SupayContext) SupayCore.queryPayOrder(cxt);
        log.debug("交易状态：{} 信息：{} 耗时：{} 接口响应数据：{}", cxt.hasError(), cxt.getMsg(), cxt.duration(), JSONUtil.toJsonStr(cxt.getResponse()));

    }
}