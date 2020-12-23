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
import cn.org.supay.core.stats.Invoker;
import cn.org.supay.core.stats.SupayStats;
import lombok.extern.slf4j.Slf4j;

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
     * @param parentInvoke
     * @param method
     */
    public void beforeInvoke(SupayContext<? extends Request, ? extends Response> ctx, Invoker parentInvoke, Method method) {
        if (parentInvoke == null) {
            // 根从0开始
            ctx.setCurrentInvoke(new Invoker(ctx.getChannelConfig().getChannelType(),
                    0, this.targetService.getClass().getSimpleName(), method.getName()));
        } else {
            Invoker currentInvoke = new Invoker(ctx.getChannelConfig().getChannelType(),
                    parentInvoke.getInvokeLevel() + 1, this.targetService.getClass().getSimpleName(), method.getName());
            // 本层调用invoke设置为上层的子层
            parentInvoke.setNextInvoke(currentInvoke);
            ctx.setCurrentInvoke(currentInvoke);
        }
        ctx.getCurrentInvoke().start();
    }

    /**
     * 代理调用后
     * @param ctx
     * @param parentInvoke
     */
    public void afterInvoke(SupayContext<? extends Request, ? extends Response> ctx, Invoker parentInvoke) {
        ctx.getCurrentInvoke().end();
    }

    /**
     * 代理调用后
     * @param ctx
     * @param parentInvoke
     */
    public void finishInvoke(SupayContext<? extends Request, ? extends Response> ctx, Invoker parentInvoke) {
        Invoker currentInvoke = ctx.getCurrentInvoke();
        // 开启统计且在最上层统计
        if (SupayCoreConfig.isEnableStats() && currentInvoke.getInvokeLevel() == 0) {
            SupayStats supayStats = SupayCoreConfig.getStats();
            if (ctx.isSuccess()) {
                supayStats.incrementSuccess(ctx.getChannelConfig().getChannelType(), ctx.getChannelInvoke(currentInvoke).getInvokeCost());
            } else {
                supayStats.incrementFailed(ctx.getChannelConfig().getChannelType(), ctx.getChannelInvoke(currentInvoke).getInvokeCost());
            }
        }

        log.debug("[调用][{}#{}]服务调用完成，耗时：{}ms 结果：{} {} 响应数据：{}", currentInvoke.getInvokeService(),
                currentInvoke.getInvokeMethod(), currentInvoke.getInvokeCost(), ctx.isSuccess(), ctx.getMsg(), JSONUtil.toJsonStr(ctx.getResponse()));

        // 返回上层前，将上层的invoke设置到上下文中
        ctx.setCurrentInvoke(parentInvoke == null?ctx.getCurrentInvoke():parentInvoke);
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
        // 上层的调用作为本层的父
        Invoker parentInvoke = ctx.getCurrentInvoke();
        try {
            //方法执行，参数：target 目标对象 arr参数数组
            this.beforeInvoke(ctx, parentInvoke, method);
            // 前置拦截器
            this.nextBefore(ctx);
            ctx = (SupayContext<? extends Request, ? extends Response>) method.invoke(targetService, args);
            // 后置拦截器
            ctx = this.nextAfter(ctx);
             this.afterInvoke(ctx, parentInvoke);
        } catch (Exception e) {
            log.error("[调用][{}#{}]服务调用异常：", this.targetService.getClass().getSimpleName(), method.getName(), e);
            ctx.fail("服务调用异常");
            // 后置拦截器
            ctx = this.nextAfter(ctx);
            this.afterInvoke(ctx, parentInvoke);
        } finally {
            this.finishInvoke(ctx, parentInvoke);
        }
        return ctx;
    }

    /**
     * 创建实际代理服务
     * @return
     */
    public abstract ChannelPayService newProxyInstance();

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