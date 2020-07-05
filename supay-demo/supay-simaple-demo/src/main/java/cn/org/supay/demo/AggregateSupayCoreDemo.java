/*******************************************************************************
 * @(#)AliSupayCoreDemo.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.demo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.org.supay.core.SupayCore;
import cn.org.supay.core.channel.aggregate.data.SupayPagePayRequest;
import cn.org.supay.core.channel.aggregate.data.SupayPayParamWxApp;
import cn.org.supay.core.channel.aggregate.data.SupayPayRequest;
import cn.org.supay.core.channel.wx.WxApiType;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

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
public class AggregateSupayCoreDemo {

    private static Props props;
    private static  SupayChannelConfig aliChannelConfig;
    private static  SupayChannelConfig wxChannelConfig;
    // 初始化
    static {
        props = new Props("config/my-ali-pay.conf");
        // 初始化配置
        aliChannelConfig = SupayChannelConfig.builder()
                .rootSecretKey(props.getStr("ali.publicKey"))
                .appId(props.getStr("ali.appId")).appSecret(props.getStr("ali.appSecret")).appName("支付宝应用-支付")
                .mchId(props.getStr("ali.mchId")).mchName("支付宝商户").mchSecretKey(props.getStr("ali.mchSecretKey"))
                .channelType(SupayChannelType.ALIPAY).apiBaseUrl("https://openapi.alipaydev.com/gateway.do")
                .build()
                .register();

        props = new Props("config/my-wx-pay.conf");
        // 初始化配置
        wxChannelConfig = SupayChannelConfig.builder()
                .appId(props.getStr("wx.appId")).appSecret(props.getStr("wx.appSecret")).appName("微信公众号-支付")
                .mchId(props.getStr("wx.mchId")).mchSecretKey(props.getStr("wx.mchSecretKey")).mchName("微信商户")
                .channelType(SupayChannelType.WECHAT).apiBaseUrl(WxApiType.BASE_URL_CHINA1.getUrl())
                .build().register();

    }

    public static void main(String[] args) {

        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文
        SupayContext cxt = SupayPagePayRequest.builder()
                .tradeNo(orderCode)
                .tradeName("测试网页支付")
                .amount(new BigDecimal(1))
                .returnUrl("http://taobao.com")
                .notifyUrl("http://taobao.com")
                .payType(SupayPayType.WX_H5_PAY)
//                .payType(SupayPayType.ALI_PAGE_PAY)
                .payParam(SupayPayParamWxApp.builder().appId("aa").build())
                .build()
//                .toContext(aliChannelConfig.getAppId(), false);
                .toContext(wxChannelConfig.getAppId(), false);

        // 本地模拟支付
//        cxt.setLocalMock(true);
        // 调用支付接口
        cxt = (SupayContext) SupayCore.pay(cxt);

        log.debug("交易状态：{} 信息：{} 耗时：{} 接口响应数据：{}", cxt.isSuccess(),
                cxt.getMsg(), cxt.duration(), JSONUtil.toJsonStr(cxt.getResponse()));

    }
}