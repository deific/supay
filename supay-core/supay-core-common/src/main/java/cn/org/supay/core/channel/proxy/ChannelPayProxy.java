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
import cn.org.supay.core.stats.InvokeStats;
import cn.org.supay.core.stats.SupayStats;
import lombok.extern.slf4j.Slf4j;
import sun.plugin2.message.Message;

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
public abstract class ChannelPayProxy extends SupayFilterChain  {

    /** 代理 */
    protected ChannelPayService targetService;

    public ChannelPayProxy(ChannelPayService targetService) {
        this.targetService = targetService;
    }

    /**
     * 代理调用前
     * @param ctx
     */
    public void beforeInvoke(SupayContext<? extends Request, ? extends Response> ctx) {
    }

    /**
     * 代理调用后
     * @param ctx
     */
    public void afterInvoke(SupayContext<? extends Request, ? extends Response> ctx) {
    }

    /**
     * 代理调用后
     * @param ctx
     */
    public void finish(SupayContext<? extends Request, ? extends Response> ctx) {
        // 开启统计且在最上层统计
        if (SupayCoreConfig.isEnableStats() && ctx.getCurrentInvoke().getInvokeLevel() == 0) {
            SupayStats supayStats = SupayCoreConfig.getSupayStats();
            if (ctx.isSuccess()) {
                supayStats.incrementSuccess(ctx.getChannelConfig().getChannelType(), ctx.getChannelInvoke(ctx.getCurrentInvoke()).getInvokeCost());
            } else {
                supayStats.incrementFailed(ctx.getChannelConfig().getChannelType(), ctx.getChannelInvoke(ctx.getCurrentInvoke()).getInvokeCost());
            }
        }
        log.debug("[调用]服务调用完成，耗时：{}ms 结果：{}", ctx.duration(), JSONUtil.toJsonStr(ctx.getResponse()));
    }

    /**
     * 实际代理方法调用
     * @param method
     * @param args
     * @return
     */
    protected Object invoke(Method method, Object[] args) {
        log.debug("[调用][{}#{}]正在调用服务...", this.targetService.getClass().getSimpleName(), method.getName());
        SupayContext<? extends Request, ? extends Response> ctx = (SupayContext<? extends Request, ? extends Response>)args[0];
        boolean isOk = checkContext(ctx);
        if (!isOk) {
            return ctx;
        }
        InvokeStats currentInvoke = ctx.getCurrentInvoke();
        ctx.startInvoke(this.targetService.getClass().getSimpleName(), method.getName(), currentInvoke);
        try {
            // 前置拦截器
            this.nextBefore(ctx);
            //方法执行，参数：target 目标对象 arr参数数组
            this.beforeInvoke(ctx);
            ctx = (SupayContext<? extends Request, ? extends Response>) method.invoke(targetService, args);
            this.afterInvoke(ctx);
            // 后置拦截器
            ctx = this.nextAfter(ctx);
        } catch (Exception e) {
            log.error("[调用]服务调用异常：", e);
        } finally {
            this.finish(ctx);
            ctx.endInvoke(this.targetService.getClass().getSimpleName(), method.getName(), currentInvoke);
        }
        return ctx;
    }

    /**
     * 获取实际代理服务
     * @return
     */
    public abstract ChannelPayService getProxyService();

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