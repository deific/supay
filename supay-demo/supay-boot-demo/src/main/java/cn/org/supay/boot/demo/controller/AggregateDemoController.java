/*******************************************************************************
 * @(#)AggregateDemoController.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.boot.demo.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.setting.dialect.Props;
import cn.org.supay.core.SupayCore;
import cn.org.supay.core.channel.aggregate.data.*;
import cn.org.supay.core.channel.alipay.data.AliPayPageRequest;
import cn.org.supay.core.channel.alipay.data.AliPayPageResponse;
import cn.org.supay.core.channel.wx.WxApiType;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * <b>Application name：</b> AggregateDemoController.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月30日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Controller
@RequestMapping("/aggregate")
public class AggregateDemoController {

    private static  SupayChannelConfig channelConfig;

    // 初始化
    static {
        Props alipayProps = new Props("./config/my-ali-pay.conf");
        Props wxpayProps = new Props("./config/my-wx-pay.conf");
        // 初始化渠道参数配置
        channelConfig = SupayChannelConfig.builder()
                .rootSecretKey(alipayProps.getStr("ali.publicKey"))
                .appId(alipayProps.getStr("ali.appId")).appSecret(alipayProps.getStr("ali.appSecret")).appName("支付宝应用-支付")
                .mchId(alipayProps.getStr("ali.mchId")).mchName("支付宝商户").mchSecretKey(alipayProps.getStr("ali.mchSecretKey"))
                .channelType(SupayChannelType.ALIPAY).apiBaseUrl("https://openapi.alipaydev.com/gateway.do")
                .build().register();
        // 初始化渠道参数配置
        channelConfig = SupayChannelConfig.builder()
                .appId(wxpayProps.getStr("wx.appId")).appSecret(wxpayProps.getStr("wx.appSecret")).appName("微信公众号-支付")
                .mchId(wxpayProps.getStr("wx.mchId")).mchSecretKey(wxpayProps.getStr("wx.mchSecretKey")).mchName("微信商户")
                .channelType(SupayChannelType.WECHAT).apiBaseUrl(WxApiType.BASE_URL_CHINA1.getUrl())
                .build().register();
        ;
    }


    /**
     * 跳到支付宝支付页面
     * 针对实时支付,即时付款
     * @param price       金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toAliPagePay.html", produces = "text/html;charset=UTF-8")
    public HttpEntity<String> toAliPagePay(BigDecimal price) {
        //及时收款
        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文参数
        SupayContext cxt = SupayPagePayRequest.builder()
                .tradeNo(orderCode)
                .payType(SupayPayType.ALI_PAGE_PAY)
                .tradeName("测试网页支付")
                .amount(price)
                .returnUrl("http://taobao.com")
                .build()
                .toContext(channelConfig.getAppId(), false);

        // 调用支付接口
        cxt = SupayCore.pay(cxt);

        String result = ((AliPayPageResponse)cxt.getResponse()).getBody();

        return new HttpEntity<>(result);
    }

    /**
     * 跳到支付宝支付页面
     * 针对实时支付,即时付款
     * @param price       金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toAliAppPay.html", produces = "text/html;charset=UTF-8")
    public HttpEntity<String> toAliAppPay(BigDecimal price) {
        //及时收款
        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文参数
        SupayContext cxt = SupayAppPayRequest.builder()
                .tradeNo(orderCode)
                .payType(SupayPayType.ALI_APP_PAY)
                .tradeName("测试APP支付")
                .amount(price)
                .returnUrl("http://taobao.com")
                .build()
                .toContext(channelConfig.getAppId(), false);

        // 调用支付接口
        cxt = SupayCore.pay(cxt);

        String result = ((SupayAppPayResponse)cxt.getResponse()).getRedirectPageBody();
        return new HttpEntity<>(result);
    }
}