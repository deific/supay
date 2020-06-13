/*******************************************************************************
 * @(#)FilterChain.java 2020年05月18日 22:23
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.filter;

import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;

/**
 * <b>Application name：</b> FilterChain.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
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
    SupayContext<? extends Request, ? extends Response> nextBefore(SupayContext<? extends Request, ? extends Response> ctx);

    /**
     * 下一个after
     * @param ctx
     * @return
     */
    SupayContext<? extends Request, ? extends Response> nextAfter(SupayContext<? extends Request, ? extends Response> ctx);

}