/*******************************************************************************
 * @(#)SpayInterceptorChain.java 2020年05月18日 21:52
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.filter;

import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Application name：</b> SpayInterceptorChain.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月18日 21:52 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@SuperBuilder(builderMethodName = "chainBuilder")
@NoArgsConstructor
public class SpayFilterChain implements FilterChain {
    /** 拦截器 */
    protected List<SpayFilter> filters;
    /** 拦截器位置*/
    protected int chainPos = 0;

    @Override
    public int getCurrent() {
        return chainPos;
    }

    @Override
    public SpayContext<? extends Request, ? extends Response> nextBefore(SpayContext<? extends Request, ? extends Response> ctx) {
        if (filters != null && chainPos < filters.size()) {
            SpayFilter filter = filters.get(chainPos);
            log.debug("[before-{}]执行过滤器before方法:{}", this.getCurrent(), filter.getClass().getName());
            chainPos ++;
            return filter.before(ctx, this);
        } else {
            return ctx;
        }
    }

    @Override
    public SpayContext<? extends Request, ? extends Response> nextAfter(SpayContext<? extends Request, ? extends Response> ctx) {
        chainPos --;
        if (filters != null && chainPos > -1) {
            SpayFilter filter = filters.get(chainPos);
            log.debug("[after-{}]执行过滤器after方法:{}", this.getCurrent(), filter.getClass().getName());
            filter.after(ctx, this);
            return ctx;
        }
        return ctx;
    }

    /**
     * 添加过滤器
     * @param filter
     * @return 过滤器链
     */
    public SpayFilterChain addFilter(SpayFilter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        if (filter != null) {
            filters.add(filter);
        }
        return this;
    }

}