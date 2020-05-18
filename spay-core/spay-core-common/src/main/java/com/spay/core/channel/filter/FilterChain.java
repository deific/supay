/*******************************************************************************
 * @(#)FilterChain.java 2020年05月18日 22:23
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.filter;

import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;

/**
 * <b>Application name：</b> FilterChain.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月18日 22:23 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface FilterChain {

    /**
     * 获取当前位置
     * @return
     */
    int getCurrent();
    /**
     * 下一个before
     * @param ctx
     * @return
     */
    SpayContext<? extends Request, ? extends Response> nextBefore(SpayContext<? extends Request, ? extends Response> ctx);

    /**
     * 下一个after
     * @param ctx
     * @return
     */
    SpayContext<? extends Request, ? extends Response> nextAfter(SpayContext<? extends Request, ? extends Response> ctx);

}