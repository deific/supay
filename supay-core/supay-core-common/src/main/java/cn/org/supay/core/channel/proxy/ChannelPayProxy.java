/*******************************************************************************
 * @(#)ChannelPayService.java 2020年05月15日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.proxy;

import cn.hutool.json.JSONUtil;
import cn.org.supay.core.channel.ChannelPayService;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.filter.SupayFilterChain;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

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
        log.debug("[调用][{}#{}]正在调用服务...", this.proxyService.getClass().getSimpleName(), method.getName());
        SupayContext<? extends Request, ? extends Response> ctx = (SupayContext<? extends Request, ? extends Response>)args[0];
        ctx.startInvoke();
        long startTime = System.currentTimeMillis();
        try {
            boolean isOk = checkContext(ctx);
            if (!isOk) {
                return ctx;
            }
            // 拦截器
            ctx = this.nextBefore(ctx);
            try {
                ctx = (SupayContext<? extends Request, ? extends Response>) method.invoke(this.proxyService, ctx);
            } catch (Exception e) {
                log.error("[调用]调用异常：", e);
                ctx.fail("调用异常：" + e.getMessage());
            }
            ctx = this.nextAfter(ctx);
            return ctx;
        } catch (Exception e) {
            log.error("[调用]调用异常：", e);
            return ctx.fail("调用异常：" + e.getMessage());
        } finally {
            long currentDuration = System.currentTimeMillis() - startTime;
            ctx.endInvoke();
            // 首层调用
            if (SupayCoreConfig.isEnableStats() && ctx.getInvokeLevel() == 0) {
                SupayCoreConfig.getSupayStats().totalCount.incrementAndGet();
                if (ctx.isSuccess()) {
                    SupayCoreConfig.getSupayStats().totalSuccess.incrementAndGet();
                } else {
                    SupayCoreConfig.getSupayStats().totalFailed.incrementAndGet();
                }
                SupayCoreConfig.getSupayStats().invokeCosts.addAndGet(ctx.duration());
            }
            log.debug("[调用]累计耗时：{}ms 当前调用耗时：{}ms 结果：{}", ctx.duration(), currentDuration, JSONUtil.toJsonStr(ctx.getResponse()));
        }
    }

    /**
     * 检查请求上下文
     * @param ctx
     * @return
     */
    private boolean checkContext(SupayContext<? extends Request, ? extends Response> ctx) {
        if (ctx.hasError()) {
            return false;
        }
        SupayChannelConfig channelConfig = ctx.getChannelConfig();
        if (channelConfig == null) {
            ctx.fail("请配置调用渠道参数");
            return false;
        }
        Request request = ctx.getRequest();
        if (request == null) {
            ctx.fail("请求配置请求参数");
            return false;
        }

        return true;
    }
}