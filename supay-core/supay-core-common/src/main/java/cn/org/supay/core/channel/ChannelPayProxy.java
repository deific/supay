/*******************************************************************************
 * @(#)ChannelPayService.java 2020年05月15日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel;

import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.filter.SupayFilterChain;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * <b>Application name：</b> ChannelPayService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class ChannelPayProxy extends SupayFilterChain implements InvocationHandler  {

    /** 代理 */
    private ChannelPayService proxyService;

    public ChannelPayProxy(ChannelPayService proxyService) {
        this.proxyService = proxyService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.debug("[支付]调用渠道服务：{}", this.proxyService.getClass().getName());
        SupayContext<? extends Request, ? extends Response> ctx = (SupayContext<? extends Request, ? extends Response>) args[0];
        if (ctx.getStartTime() == null) {
            ctx.setStartTime(new Date());
        }

        SupayChannelConfig channelConfig = ctx.getChannelConfig();
        if (channelConfig == null) {
            return ctx.fail("请配置支付渠道参数");
        }

        long startTime = System.currentTimeMillis();
        try {
            // 拦截器
            this.nextBefore(ctx);
            ctx = (SupayContext<? extends Request, ? extends Response>) method.invoke(this.proxyService, ctx);
            this.nextAfter(ctx);
            return ctx;
        } catch (Exception e) {
            log.error("支付异常：", e);
            return ctx.fail("支付异常：" + e.getMessage());
        } finally {
            ctx.setEndTime(new Date());
            log.debug("[支付] 支付耗时：{} 结果：{}", ctx.getEndTime().getTime() - startTime, ctx.getResponse());
        }
    }
}