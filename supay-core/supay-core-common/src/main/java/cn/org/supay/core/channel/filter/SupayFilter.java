/*******************************************************************************
 * @(#)SupayFilter.java 2020年05月16日 10:16
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.filter;

import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;

/**
 * <b>Application name：</b> SupayFilter.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 10:16 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface SupayFilter {


    /**
     * 过滤
     * @param ctx
     * @param chain
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> before(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextBefore(ctx);
    }

    /**
     * 过滤
     * @param ctx
     * @param chain
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextAfter(ctx);
    }

}