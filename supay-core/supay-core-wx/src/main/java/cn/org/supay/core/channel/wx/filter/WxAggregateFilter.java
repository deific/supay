/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.filter;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.org.supay.core.channel.aggregate.data.SupayPayRequest;
import cn.org.supay.core.channel.aggregate.data.SupayPayResponse;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import cn.org.supay.core.filter.FilterChain;
import cn.org.supay.core.filter.SupayFilter;
import cn.org.supay.core.channel.wx.data.WxPayBaseRequest;
import cn.org.supay.core.channel.wx.data.WxPayUnifiedOrderRequest;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
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
public class WxAggregateFilter implements SupayFilter {

    @Override
    public SupayContext<? extends Request, ? extends Response> before(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        // 判断是否可处理
        SupayChannelType targetChannel = ctx.getChannelConfig().getChannelType();
        if (!SupayChannelType.WECHAT.equals(targetChannel)) {
            return chain.nextBefore(ctx);
        }

        // 转换支付参数
        if (ctx.getRequest() instanceof SupayPayRequest) {
            SupayPayRequest request = (SupayPayRequest) ctx.getRequest();
            WxPayBaseRequest wxRequest = WxPayUnifiedOrderRequest.builder()
                    .body(request.getTradeName())
                    .outTradeNo(request.getTradeNo())
                    .notifyUrl(request.getNotifyUrl())
                    .totalFee(request.getAmount().multiply(new BigDecimal(100)).toString())
                    .timeStart(DateUtil.format(new Date(), "yyyyMMddHHmmss"))
                    .timeExpire(DateUtil.format(DateUtil.offsetMinute(new Date(), 15), "yyyyMMddHHmmss"))
                    .tradeType(SupayPayType.WX_MP_PAY.getCode())
//                        .openid(props.getStr("wx.openId"))
                    .spbillCreateIp(NetUtil.getLocalhostStr())
                    .nonceStr(String.valueOf(System.currentTimeMillis()))
                    .build();
            ctx.setRequest(wxRequest);
        }
        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        // 判断是否可处理
        SupayChannelType targetChannel = ctx.getChannelConfig().getChannelType();
        if (SupayChannelType.WECHAT.equals(targetChannel)) {
            return chain.nextAfter(ctx);
        }

        // 转换支付结果参数
        if (ctx.getResponse() instanceof WxPayUnifiedOrderResponse) {
            WxPayUnifiedOrderResponse wxResponse = (WxPayUnifiedOrderResponse) ctx.getResponse();
            SupayPayResponse payResponse = SupayPayResponse.builder()
                    .resultCode(wxResponse.getResultCode())
                    .resultMsg(wxResponse.getReturnMsg())
                    .redirectUrl(wxResponse.getMwebUrl())
                    .build();
            ctx.setResponse(payResponse);
        }

        return chain.nextAfter(ctx);
    }
}