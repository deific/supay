/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.filter;

import cn.hutool.core.date.DateUtil;
import cn.org.supay.core.channel.aggregate.data.SupayPayRequest;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
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

        SupayChannelType targetChannel = ctx.getChannelConfig().getChannelType();
        if (SupayChannelType.WECHAT.equals(targetChannel)) {
            SupayPayRequest request = (SupayPayRequest) ctx.getRequest();
            WxPayBaseRequest wxRequest = WxPayUnifiedOrderRequest.builder()
                    .body("测试微信支付订单")
                    .outTradeNo(request.getBizPayNo())
                    .notifyUrl(request.getNotifyUrl())
                    .totalFee(request.getAmount().multiply(new BigDecimal(100)).toString())
                    .timeStart(DateUtil.format(new Date(), "yyyyMMddHHmmss"))
                    .timeExpire(DateUtil.format(DateUtil.offsetMinute(new Date(), 15), "yyyyMMddHHmmss"))
                    .tradeType(SupayPayType.WX_MP_PAY.getCode())
//                        .openid(props.getStr("wx.openId"))
                    .spbillCreateIp("127.0.0.1")
                    .nonceStr(String.valueOf(System.currentTimeMillis()))
                    .build();
            ctx.setRequest(wxRequest);
        }
        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {

        // 将响应转换为


        return chain.nextAfter(ctx);
    }
}