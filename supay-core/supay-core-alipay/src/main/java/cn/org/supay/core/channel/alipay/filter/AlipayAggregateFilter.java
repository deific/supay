/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.filter;

import cn.org.supay.core.channel.aggregate.context.AggregateContext;
import cn.org.supay.core.channel.aggregate.data.SupayPagePayRequest;
import cn.org.supay.core.channel.aggregate.data.SupayPagePayResponse;
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
        if (!ctx.isAggregate()) {
            return chain.nextBefore(ctx);
        }

        // 转换方法参数
        Request request = ctx.getRequest();
        // 支付方法
        if (request instanceof SupayPagePayRequest) {
            SupayPayRequest payRequest = (SupayPayRequest) request;
            AliPayPageRequest pageRequest = AliPayPageRequest.builder()
                    .payType(((SupayPayRequest) request).getPayType())
                    .outTradeNo(payRequest.getTradeNo())
                    .subject(payRequest.getTradeName())
                    .totalAmount(payRequest.getAmount().toString())
                    .returnUrl(payRequest.getReturnUrl()).build();

            // 用转换后的具体渠道request覆盖将原请求参数，后续转给具体渠道服务执行
            // 原请求暂存originRequest，返回时与request交换
            ctx.setRequest(pageRequest);
        }
        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        SupayChannelType targetChannel = ctx.getChannelConfig().getChannelType();
        if (!ctx.isAggregate()) {
            return chain.nextAfter(ctx);
        }

        AggregateContext thisCtx = (AggregateContext) ctx;
        // 将响应转换为
        Response response = thisCtx.getResponse();

        // 支付方法
        if (response instanceof AliPayPageResponse) {
            AliPayPageResponse pageResponse = (AliPayPageResponse) response;
            SupayPayResponse payResponse = SupayPagePayResponse.builder()
                    .redirectPageBody(pageResponse.getBody())
                    .build();

            // 将转换后的响应暂存
            thisCtx.setOriginResponse(payResponse);
        }

        // 返回前交换请求和响应，保持接口调用时输入输出参数一致性
        thisCtx.switchRequest();
        thisCtx.switchResponse();

        return chain.nextAfter(thisCtx);
    }
}