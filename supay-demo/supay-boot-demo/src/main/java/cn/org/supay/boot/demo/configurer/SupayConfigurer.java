/*******************************************************************************
 * @(#)SupayConfigurer.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.boot.demo.configurer;

import cn.hutool.setting.dialect.Props;
import cn.org.supay.core.channel.wx.WxApiType;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.enums.SupayChannelType;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * <b>Application name：</b> SupayConfigurer.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月30日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Configuration
public class SupayConfigurer {

    @PostConstruct
    public void init() {
        Props alipayProps = new Props("./config/my-ali-pay.conf");
        Props wxpayProps = new Props("./config/my-wx-pay.conf");
        // 初始化渠道参数配置
        SupayChannelConfig.builder()
                .rootSecretKey(alipayProps.getStr("ali.publicKey"))
                .appId(alipayProps.getStr("ali.appId")).appSecret(alipayProps.getStr("ali.appSecret")).appName("支付宝应用-支付")
                .mchId(alipayProps.getStr("ali.mchId")).mchName("支付宝商户").mchSecretKey(alipayProps.getStr("ali.mchSecretKey"))
                .channelType(SupayChannelType.ALIPAY).apiBaseUrl("https://openapi.alipaydev.com/gateway.do")
                .build().register();
        // 初始化渠道参数配置
        SupayChannelConfig.builder()
                .appId(wxpayProps.getStr("wx.appId")).appSecret(wxpayProps.getStr("wx.appSecret")).appName("微信公众号-支付")
                .mchId(wxpayProps.getStr("wx.mchId")).mchSecretKey(wxpayProps.getStr("wx.mchSecretKey")).mchName("微信商户")
                .channelType(SupayChannelType.WECHAT).apiBaseUrl(WxApiType.BASE_URL_CHINA1.getUrl())
                .build().register();
        ;
    }
}