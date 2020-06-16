/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.filter;

import cn.hutool.core.date.DateUtil;
import cn.org.supay.core.SupayCore;
import cn.org.supay.core.channel.BasePayChannelService;
import cn.org.supay.core.channel.aggregate.data.SupayRequest;
import cn.org.supay.core.channel.aggregate.data.SupayResponse;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.channel.filter.FilterChain;
import cn.org.supay.core.channel.filter.SupayFilter;
import cn.org.supay.core.channel.wx.data.WxPayBaseRequest;
import cn.org.supay.core.channel.wx.data.WxPayUnifiedOrderRequest;
import cn.org.supay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.utils.SupayUtils;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Date;

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

        SupayContext<SupayRequest, SupayResponse> thisCtx = SupayUtils.checkAndConvertType(ctx, SupayRequest.class, SupayResponse.class);
        if (thisCtx.hasError()) {
            thisCtx.fail("错误的类型");
            return chain.nextBefore(thisCtx);
        }
        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextAfter(ctx);
    }
}