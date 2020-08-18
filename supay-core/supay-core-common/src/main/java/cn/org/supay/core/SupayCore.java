/*******************************************************************************
 * @(#)SupayCore.java 2020年05月16日 08:49
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core;

import cn.org.supay.core.channel.ChannelPayService;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.config.SupayConfiguration;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.enums.SupayChannelType;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

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
public class SupayCore {

    static {
        SupayConfiguration.init();
    }

    /**
     * 根据上下文获取渠道支付服务
     * @param ctx
     * @return
     */
    public static ChannelPayService getPayChannelService(SupayContext<? extends Request, ? extends Response> ctx) {
        ctx.setStartTime(new Date());
        SupayChannelConfig channelConfig = ctx.getChannelConfig();

        // 如果启用本地模拟
        SupayChannelType channelType = channelConfig.getChannelType();
        if (ctx.isLocalMock()) {
            channelType = SupayChannelType.MOCK;
            return SupayCoreConfig.getPayChannelService(channelType);
        }
        if (ctx.isAggregate()) {
            channelType = SupayChannelType.AGGREGATE_PAY;
            return SupayCoreConfig.getPayChannelService(channelType);
        }
        return SupayCoreConfig.getPayChannelService(channelType);
    }

    /**
     * 根据上下文获取渠道支付服务
     * @param channelType
     * @return
     */
    public static ChannelPayService getPayChannelService(SupayChannelType channelType) {
        return SupayCoreConfig.getPayChannelService(channelType);
    }

    /**
     * 直接支付
     * @param ctx 支付上下文
     * @return 支付上下文
     */
    public static SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        ChannelPayService proxyService = getPayChannelService(ctx);
        return proxyService.pay(ctx);
    }


    /**
     * 直接支付
     * @param ctx 支付上下文
     * @return 支付上下文
     */
    public static SupayContext<? extends Request, ? extends Response> payQuery(SupayContext<? extends Request, ? extends Response> ctx) {
        ChannelPayService proxyService = getPayChannelService(ctx);
        return proxyService.queryPayTrade(ctx);
    }

    /**
     * 确认支付
     * @param ctx
     * @return
     */
    public static SupayContext<? extends Request, ? extends Response> confirm(SupayContext<? extends Request, ? extends Response> ctx) {
        ChannelPayService proxyService = getPayChannelService(ctx);
        return proxyService.confirm(ctx);
    }

    /**
     * 退款
     * @param ctx
     * @return
     */
    public static SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        ChannelPayService proxyService = getPayChannelService(ctx);
        return proxyService.refund(ctx);
    }

    /**
     * 批量查询交易信息
     * @param ctx
     * @return
     */
    public static SupayContext<? extends Request, ? extends Response> refundQuery(SupayContext<? extends Request, ? extends Response> ctx) {
        ChannelPayService proxyService = getPayChannelService(ctx);
        return proxyService.queryRefundTrade(ctx);
    }

    /**
     * 发送红包
     * @param ctx
     * @return
     */
    public static SupayContext<? extends Request, ? extends Response> sendRedPackage(SupayContext<? extends Request, ? extends Response> ctx) {
        ChannelPayService proxyService = getPayChannelService(ctx);
        return proxyService.sendRedPackage(ctx);
    }
}