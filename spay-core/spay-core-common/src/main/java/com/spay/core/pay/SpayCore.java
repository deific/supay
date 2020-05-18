/*******************************************************************************
 * @(#)SpayCore.java 2020年05月16日 08:49
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.pay;

import com.spay.core.channel.PayChannelService;
import com.spay.core.config.SpayChannelConfig;
import com.spay.core.config.SpayConfig;
import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * <b>Application name：</b> SpayCore.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 08:49 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class SpayCore {

    /** 私有构造函数 */
    private SpayCore() {}

    /**
     * 直接支付
     * @param ctx 支付上下文
     * @return 支付上下文
     */
    public static SpayContext<? extends Request, ? extends Response> pay(SpayContext<? extends Request, ? extends Response> ctx) {
        SpayChannelConfig channelConfig = ctx.getChannelConfig();
        if (channelConfig == null) {
            return ctx.fail("请配置支付渠道参数");
        }
        ctx.setStartTime(new Date());
        try {
            PayChannelService payService = SpayConfig.getPayService(channelConfig.getChannelType());
            if (payService != null) {
                // 拦截器
                ctx.nextBefore(ctx);
                payService.pay(ctx);
                ctx.nextAfter(ctx);
                return ctx;
            } else {
                return ctx.fail("不支持该渠道支付");
            }
        } catch (Exception e) {
            return ctx.fail("支付异常：" + e.getMessage());
        } finally {
            ctx.setEndTime(new Date());
            log.debug("[支付] 支付耗时：{} 结果：{}", ctx.duration(), ctx.getResponse());
        }
    }
}