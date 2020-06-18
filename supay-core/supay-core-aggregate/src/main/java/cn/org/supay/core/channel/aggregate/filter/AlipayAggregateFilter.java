/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.filter;

import cn.org.supay.core.channel.aggregate.data.SupayPayRequest;
import cn.org.supay.core.channel.alipay.data.AliPayPageRequest;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.filter.FilterChain;
import cn.org.supay.core.filter.SupayFilter;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
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
public class AlipayAggregateFilter implements SupayFilter {
    @Override
    public SupayContext<? extends Request, ? extends Response> before(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        SupayChannelType targetChannel = ctx.getChannelConfig().getChannelType();
        if (!SupayChannelType.ALIPAY.equals(targetChannel)) {
            return chain.nextBefore(ctx);
        }

        Request request = ctx.getRequest();

        // 转换方法参数
        // 支付方法
        if (request instanceof SupayPayRequest) {
            SupayPayRequest payRequest = (SupayPayRequest) request;
            AliPayPageRequest pageRequest = AliPayPageRequest.builder()
                    .outTradeNo(payRequest.getBizPayNo())
                    .subject(payRequest.getBizPayNo())
                    .totalAmount(payRequest.getAmount().toString())
                    .returnUrl(payRequest.getReturnUrl()).build();
            ctx.setRequest(pageRequest);
        }

        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {

        // 将响应转换为


        return chain.nextAfter(ctx);
    }
}