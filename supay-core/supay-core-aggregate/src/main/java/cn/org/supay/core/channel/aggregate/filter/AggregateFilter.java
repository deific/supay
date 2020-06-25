/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.org.supay.core.channel.aggregate.data.SupayPayRequest;
import cn.org.supay.core.channel.aggregate.data.SupayPayResponse;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.filter.FilterChain;
import cn.org.supay.core.filter.SupayFilter;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.utils.BeanUtils;
import cn.org.supay.core.utils.SupayUtils;
import lombok.Data;
import lombok.experimental.SuperBuilder;
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
        ctx = convertSubContext(ctx);

        SupayContext<SupayPayRequest, SupayPayResponse> thisCtx = SupayUtils.checkAndConvertType(ctx, SupayPayRequest.class, SupayPayResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }

        // 检查支付类型和所使用渠道是否匹配
        if (!thisCtx.getRequest().getPayType().getChannel().equals(thisCtx.getChannelConfig().getChannelType())) {
            return ctx.fail(StrUtil.format("本次请求支付方式{} 与所使用渠道{}不匹配",
                    thisCtx.getRequest().getPayType().getName(), thisCtx.getChannelConfig().getChannelType().getName()));
        }
        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        ctx = convertSupayContext((SubContext)ctx);
        SupayContext<SupayPayRequest, SupayPayResponse> thisCtx = SupayUtils.checkAndConvertType(ctx, SupayPayRequest.class, SupayPayResponse.class);
        if (thisCtx.hasError()) {
        }
        return chain.nextAfter(ctx);
    }

    /**
     * 将上下文转换子类上下文，用于保存原始的Request和Response
     * @param ctx
     */
    private SubContext convertSubContext(SupayContext<? extends Request, ? extends Response> ctx) {
        SubContext subContext = SubContext.builder().build();
        BeanUtils.copyProperties(ctx, subContext);
        subContext.setOriginRequest(ctx.getRequest());
        subContext.setOriginResponse(ctx.getResponse());
        return subContext;
    }

    /**
     * 将上下文转换子类上下文，用于保存原始的Request和Response
     * @param ctx
     */
    private SupayContext convertSupayContext(SubContext<? extends Request, ? extends Response> ctx) {
        SupayContext context = SupayContext.builder().build();
        BeanUtils.copyProperties(ctx, context);
        context.setRequest(ctx.getOriginRequest());
        return context;
    }

}

@Data
@SuperBuilder
class SubContext<R extends Request, S extends Response> extends  SupayContext<R, S> {
    private R originRequest;
    private S originResponse;
};