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
import com.spay.core.enums.SpayChannelType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
public class SpayCore implements InvocationHandler {
    /** 实际执行服务 */
    private PayChannelService targetService;

    /** 动态代理map */
    private static Map<SpayChannelType, PayChannelService> proxyMap = new HashMap<>();

    /** 私有构造函数 */
    private SpayCore(PayChannelService targetService) {
        this.targetService = targetService;
    }

    /**
     * 获取并缓存动态代理类
     * @param ctx
     * @return
     */
    private static PayChannelService getProxyChannelService(SpayContext<? extends Request, ? extends Response> ctx) {
        ctx.setStartTime(new Date());
        SpayChannelConfig channelConfig = ctx.getChannelConfig();
        SpayChannelType channelType = channelConfig == null?null:channelConfig.getChannelType();
        PayChannelService proxyService = proxyMap.get(channelType);
        if(proxyService == null) {
            PayChannelService targetService = SpayConfig.getPayService(channelType);
            if (targetService == null) {
                targetService = new PayChannelService() {
                    @Override
                    public String getPayServiceName() {
                        return "NonePayChannelService";
                    }
                };
            }
            SpayCore proxy = new SpayCore(targetService);
            proxyService = (PayChannelService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetService.getClass().getInterfaces(), proxy);
            proxyMap.put(ctx.getChannelConfig().getChannelType(), proxyService);
        }
        return proxyService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        SpayContext<? extends Request, ? extends Response> ctx = (SpayContext<? extends Request, ? extends Response>) args[0];
        SpayChannelConfig channelConfig = ctx.getChannelConfig();
        if (channelConfig == null) {
            return ctx.fail("请配置支付渠道参数");
        }
        try {
            PayChannelService payService = SpayConfig.getPayService(channelConfig.getChannelType());
            if (payService != null) {
                // 拦截器
                ctx.nextBefore(ctx);
                ctx = (SpayContext<? extends Request, ? extends Response>) method.invoke(targetService, ctx);
                ctx.nextAfter(ctx);
                return ctx;
            } else {
                return ctx.fail("不支持该渠道支付");
            }
        } catch (Exception e) {
            log.error("支付异常：", e);
            return ctx.fail("支付异常：" + e.getMessage());
        } finally {
            ctx.setEndTime(new Date());
            log.debug("[支付] 支付耗时：{} 结果：{}", ctx.duration(), ctx.getResponse());
        }
    }

    /**
     * 直接支付
     * @param ctx 支付上下文
     * @return 支付上下文
     */
    public static SpayContext<? extends Request, ? extends Response> pay(SpayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.pay(ctx);
    }


    /**
     * 直接支付
     * @param ctx 支付上下文
     * @return 支付上下文
     */
    public static SpayContext<? extends Request, ? extends Response> queryPayOrder(SpayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.queryTradeInfo(ctx);
    }

    /**
     * 确认支付
     * @param ctx
     * @return
     */
    public static SpayContext<? extends Request, ? extends Response> confirm(SpayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.confirm(ctx);
    }

    /**
     * 退款
     * @param ctx
     * @return
     */
    public static SpayContext<? extends Request, ? extends Response> refund(SpayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.refund(ctx);
    }

    /**
     * 批量查询交易信息
     * @param ctx
     * @return
     */
    public static SpayContext<? extends Request, ? extends Response> queryTradeInfo(SpayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.queryTradeInfo(ctx);
    }

    /**
     * 发送红包
     * @param ctx
     * @return
     */
    public static SpayContext<? extends Request, ? extends Response> sendRedPackage(SpayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.sendRedPackage(ctx);
    }
}