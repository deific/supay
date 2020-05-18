/*******************************************************************************
 * @(#)WxPayFilter.java 2020年05月18日 22:18
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.filter;

import com.spay.core.channel.filter.FilterChain;
import com.spay.core.channel.filter.SpayFilter;
import com.spay.core.channel.wx.data.WxMpPayData;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import com.spay.core.context.SpayContext;
import com.spay.core.context.SpayPayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import com.spay.core.enums.SpayPayType;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Application name：</b> WxPayFilter.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月18日 22:18 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class WxPayFilter implements SpayFilter {
    @Override
    public SpayContext<? extends Request, ? extends Response> before(SpayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextBefore(ctx);
    }

    @Override
    public SpayContext<? extends Request, ? extends Response> after(SpayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        // 如果是支付
        if (ctx instanceof SpayPayContext) {
            WxPayUnifiedOrderResponse response = (WxPayUnifiedOrderResponse) ctx.getResponse();
            SpayPayType payType = SpayPayType.valueOfByCode(response.getTradeType());
            // 解析和封装微信返回数据
            switch (payType) {
                case WX_MP_PAY:
                    WxMpPayData mpPayData = WxMpPayData.builder()
                            .appId(response.getAppid())
                            .prepayId(response.getPrepayId())
                            .packageStr("")
                            .nonceStr(String.valueOf(System.currentTimeMillis()))
                            .build()
                            ;
                    response.setPayData(mpPayData);

            }
        }


        return chain.nextAfter(ctx);
    }
}