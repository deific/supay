/*******************************************************************************
 * @(#)SupayFilterChain.java 2020年05月18日 21:52
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.filter;

import cn.hutool.core.collection.ListUtil;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Application name：</b> SupayFilterChain.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月18日 21:52 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@SuperBuilder(builderMethodName = "chainBuilder")
@NoArgsConstructor
public class SupayFilterChain implements FilterChain {
    /** 拦截器 */
    protected List<SupayFilter> filters;
    /** 拦截器位置*/
    protected int chainPos = 0;

    @Override
    public int getCurrent() {
        return chainPos;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> nextBefore(SupayContext<? extends Request, ? extends Response> ctx) {
        if (filters != null && chainPos < filters.size() && !ctx.hasError()) {
            SupayFilter filter = filters.get(chainPos);
            log.debug("[支付][before-{}]执行过滤器before方法:{}", this.getCurrent(), filter.getClass().getName());
            chainPos ++;
            return filter.before(ctx, this);
        } else {
            return ctx;
        }
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> nextAfter(SupayContext<? extends Request, ? extends Response> ctx) {
        if (filters != null && chainPos > 0) {
            chainPos --;
            SupayFilter filter = filters.get(chainPos);
            log.debug("[支付][after-{}]执行过滤器after方法:{}", this.getCurrent(), filter.getClass().getName());
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
    public SupayFilterChain addFilter(SupayFilter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        if (filter != null) {
            filters.add(filter);
        }
        return this;
    }

    /**
     * 添加过滤器
     * @param filter
     * @return 过滤器链
     */
    public SupayFilterChain addFilter(SupayFilter... filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        if (filter != null) {
            filters.addAll(ListUtil.toList(filter));
        }
        return this;
    }

    /**
     * 添加过滤器
     * @param filters
     * @return 过滤器链
     */
    public SupayFilterChain addFilter(List<SupayFilter> filters) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        if (filters != null && !filters.isEmpty()) {
            filters.addAll(ListUtil.toList(filters));
        }
        return this;
    }
}