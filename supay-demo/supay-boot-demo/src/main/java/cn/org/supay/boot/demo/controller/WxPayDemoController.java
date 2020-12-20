/*******************************************************************************
 * @(#)AlipayDemoController.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.boot.demo.controller;

import cn.hutool.core.util.IdUtil;
import cn.org.supay.core.SupayCore;
import cn.org.supay.core.channel.aggregate.Supay;
import cn.org.supay.core.channel.alipay.data.AliPayPageRequest;
import cn.org.supay.core.channel.alipay.data.AliPayPageResponse;
import cn.org.supay.core.channel.notify.ChannelNotifyData;
import cn.org.supay.core.channel.notify.ChannelNotifyHandler;
import cn.org.supay.core.channel.notify.ChannelNotifyType;
import cn.org.supay.core.channel.wx.notify.WxNotifyType;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.context.SupayNotifyContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * <b>Application name：</b> AlipayDemoController.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月30日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Controller
@RequestMapping("/alipay")
public class WxPayDemoController {

    /**
     * 跳到支付页面
     * 针对实时支付,即时付款
     *
     * @param price       金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toPay.html", produces = "text/html;charset=UTF-8")
    public HttpEntity<String> toPay(BigDecimal price, String appId) {
        // 调用支付接口
        SupayNotifyContext notifyContext = SupayNotifyContext.buildNotifyContext(WxNotifyType.PAY_NOTIFY, null, null);
        notifyContext = SupayCore.checkAndHandleCallbackNotify(notifyContext, new ChannelNotifyHandler() {

            @Override
            public boolean handleNotify(ChannelNotifyType notifyType, ChannelNotifyData notifyData) {
                return true;
            }
        });
        return new HttpEntity<>(notifyContext.getRetStr());
    }
}