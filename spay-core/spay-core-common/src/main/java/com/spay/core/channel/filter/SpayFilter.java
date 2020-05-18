/*******************************************************************************
 * @(#)BasePayChannelService.java 2020年05月16日 10:16
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.filter;

import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Application name：</b> BasePayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 10:16 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface SpayFilter {


    /**
     * 过滤
     * @param ctx
     * @param chain
     * @return
     */
    default SpayContext<? extends Request, ? extends Response> before(SpayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextBefore(ctx);
    }

    /**
     * 过滤
     * @param ctx
     * @param chain
     * @return
     */
    default SpayContext<? extends Request, ? extends Response> after(SpayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextAfter(ctx);
    }

}