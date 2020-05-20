/*******************************************************************************
 * @(#)SupayCore.java 2020年05月16日 08:49
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.pay;

import cn.org.supay.core.channel.PayChannelService;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;
import cn.org.supay.core.enums.SupayChannelType;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> SupayCore.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 08:49 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class SupayCore implements InvocationHandler {
    /** 实际执行服务 */
    private PayChannelService targetService;

    /** 动态代理map */
    private static Map<SupayChannelType, PayChannelService> proxyMap = new HashMap<>();

    /** 私有构造函数 */
    private SupayCore(PayChannelService targetService) {
        this.targetService = targetService;
    }

    /**
     * 获取并缓存动态代理类
     * @param ctx
     * @return
     */
    private static PayChannelService getProxyChannelService(SupayContext<? extends Request, ? extends Response> ctx) {
        ctx.setStartTime(new Date());
        SupayChannelConfig channelConfig = ctx.getChannelConfig();
        SupayChannelType channelType = channelConfig == null?null:channelConfig.getChannelType();
        PayChannelService proxyService = proxyMap.get(channelType);
        if(proxyService == null) {
            PayChannelService targetService = SupayConfig.getPayService(channelType);
            if (targetService == null) {
                targetService = new PayChannelService() {
                    @Override
                    public String getPayServiceName() {
                        return "NonePayChannelService";
                    }
                };
            }
            SupayCore proxy = new SupayCore(targetService);
            proxyService = (PayChannelService) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), targetService.getClass().getInterfaces(), proxy);
            proxyMap.put(ctx.getChannelConfig().getChannelType(), proxyService);
        }
        return proxyService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        SupayContext<? extends Request, ? extends Response> ctx = (SupayContext<? extends Request, ? extends Response>) args[0];
        SupayChannelConfig channelConfig = ctx.getChannelConfig();
        if (channelConfig == null) {
            return ctx.fail("请配置支付渠道参数");
        }
        try {
            PayChannelService payService = SupayConfig.getPayService(channelConfig.getChannelType());
            if (payService != null) {
                // 拦截器
                ctx.nextBefore(ctx);
                ctx = (SupayContext<? extends Request, ? extends Response>) method.invoke(targetService, ctx);
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
    public static SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.pay(ctx);
    }


    /**
     * 直接支付
     * @param ctx 支付上下文
     * @return 支付上下文
     */
    public static SupayContext<? extends Request, ? extends Response> queryPayOrder(SupayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.queryTradeInfo(ctx);
    }

    /**
     * 确认支付
     * @param ctx
     * @return
     */
    public static SupayContext<? extends Request, ? extends Response> confirm(SupayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.confirm(ctx);
    }

    /**
     * 退款
     * @param ctx
     * @return
     */
    public static SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.refund(ctx);
    }

    /**
     * 批量查询交易信息
     * @param ctx
     * @return
     */
    public static SupayContext<? extends Request, ? extends Response> queryTradeInfo(SupayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.queryTradeInfo(ctx);
    }

    /**
     * 发送红包
     * @param ctx
     * @return
     */
    public static SupayContext<? extends Request, ? extends Response> sendRedPackage(SupayContext<? extends Request, ? extends Response> ctx) {
        PayChannelService proxyService = getProxyChannelService(ctx);
        return proxyService.sendRedPackage(ctx);
    }
}