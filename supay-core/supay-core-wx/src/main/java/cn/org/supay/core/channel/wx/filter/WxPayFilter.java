/*******************************************************************************
 * @(#)WxPayFilter.java 2020年05月18日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.filter;

import cn.org.supay.core.filter.FilterChain;
import cn.org.supay.core.filter.SupayFilter;
import cn.org.supay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.channel.wx.data.WxMpPayData;
import cn.org.supay.core.channel.data.Request;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Application name：</b> WxPayFilter.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月18日 22:18 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class WxPayFilter implements SupayFilter {
    @Override
    public SupayContext<? extends Request, ? extends Response> before(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextAfter(ctx);
    }
}