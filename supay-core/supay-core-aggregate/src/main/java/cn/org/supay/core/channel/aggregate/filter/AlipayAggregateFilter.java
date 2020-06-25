/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.filter;

import cn.org.supay.core.channel.aggregate.data.SupayPayRequest;
import cn.org.supay.core.channel.aggregate.data.SupayPayResponse;
import cn.org.supay.core.channel.alipay.data.AliPayPageRequest;
import cn.org.supay.core.channel.alipay.data.AliPayPageResponse;
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

        // 转换方法参数
        Request request = ctx.getRequest();

        // 支付方法
        if (request instanceof SupayPayRequest) {
            SupayPayRequest payRequest = (SupayPayRequest) request;
            AliPayPageRequest pageRequest = AliPayPageRequest.builder()
                    .payType(((SupayPayRequest) request).getPayType())
                    .outTradeNo(payRequest.getTradeNo())
                    .subject(payRequest.getTradeName())
                    .totalAmount(payRequest.getAmount().toString())
                    .returnUrl(payRequest.getReturnUrl()).build();
            ctx.setRequest(pageRequest);
        }

        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        SupayChannelType targetChannel = ctx.getChannelConfig().getChannelType();
        if (!SupayChannelType.ALIPAY.equals(targetChannel)) {
            return chain.nextAfter(ctx);
        }

        // 将响应转换为
        Response response = ctx.getResponse();
        // 支付方法
        if (response instanceof AliPayPageResponse) {
            AliPayPageResponse pageResponse = (AliPayPageResponse) response;
            SupayPayResponse payResponse = SupayPayResponse.builder()
                    .redirectPageBody(pageResponse.getBody())
                    .build();
            ctx.setResponse(payResponse);
        }

        return chain.nextAfter(ctx);
    }
}