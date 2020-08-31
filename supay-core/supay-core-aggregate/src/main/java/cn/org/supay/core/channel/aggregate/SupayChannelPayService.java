/*******************************************************************************
 * @(#)AggregatePayChannelService.java 2020年05月16日 23:31
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate;

import cn.org.supay.core.SupayCore;
import cn.org.supay.core.channel.BaseChannelPayService;
import cn.org.supay.core.channel.aggregate.filter.AggregateFilter;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;

/**
 * <b>Application name：</b> SupayChannelPayService.java <br>
 * <b>Application describing： </b> 聚合支付渠道服务
 * 聚合支付是对其他第三方支付渠道（微信、支付宝等）的支付接口抽象封装为统一的请求和响应参数对方提供支付服务，不需要再去查看每一个第三方的接口文档进行调用。
 * 实际调用时根据参数中的支付渠道信息调用对应的服务完成支付处理
 * <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 23:31 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class SupayChannelPayService implements BaseChannelPayService {

    @Override
    public SupayChannelType getSupportType() {
        return SupayChannelType.AGGREGATE_PAY;
    }

    @Override
    public void register() {
        SupayCoreConfig.registerPayService(getSupportType(), this, new AggregateFilter());
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        return SupayCore.getPayChannelService(ctx.getChannelConfig().getChannelType()).pay(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> confirm(SupayContext<? extends Request, ? extends Response> ctx) {
        return SupayCore.getPayChannelService(ctx.getChannelConfig().getChannelType()).confirm(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        return SupayCore.getPayChannelService(ctx.getChannelConfig().getChannelType()).refund(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryPay(SupayContext<? extends Request, ? extends Response> ctx) {
        return SupayCore.getPayChannelService(ctx.getChannelConfig().getChannelType()).queryPay(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> sendRedPackage(SupayContext<? extends Request, ? extends Response> ctx) {
        return SupayCore.getPayChannelService(ctx.getChannelConfig().getChannelType()).sendRedPackage(ctx);
    }
}