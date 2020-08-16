/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.filter;

import cn.hutool.core.util.StrUtil;
import cn.org.supay.core.channel.aggregate.context.AggregateContext;
import cn.org.supay.core.channel.aggregate.data.*;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.filter.FilterChain;
import cn.org.supay.core.filter.SupayFilter;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.utils.SupayUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Application name：</b> AliPayFilter.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月31日 22:18 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class AggregateFilter implements SupayFilter {
    @Override
    public SupayContext<? extends Request, ? extends Response> before(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        SupayContext<SupayPayRequest, SupayPayResponse> thisCtx = SupayUtils.checkAndConvertType(ctx, SupayBaseRequest.class, SupayBaseResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }

        // 检查支付类型和所使用渠道是否匹配
        if (thisCtx.getRequest() instanceof SupayPayRequest && !thisCtx.getRequest().getPayType().getChannel().equals(thisCtx.getChannelConfig().getChannelType())) {
            return ctx.fail(StrUtil.format("本次请求支付方式{} 与所使用渠道{}不匹配",
                    thisCtx.getRequest().getPayType().getName(), thisCtx.getChannelConfig().getChannelType().getName()));
        }

        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        AggregateContext aggregateContext = (AggregateContext) ctx;
        aggregateContext.switchRequest();
        aggregateContext.switchResponse();
        SupayContext<SupayPayRequest, SupayPayResponse> thisCtx = SupayUtils.checkAndConvertType(ctx, SupayBaseRequest.class, SupayBaseResponse.class);
        return chain.nextAfter(ctx);
    }
}