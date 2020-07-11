/*******************************************************************************
 * @(#)AggregateDemoController.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.boot.demo.controller;

import cn.hutool.core.util.IdUtil;
import cn.org.supay.core.SupayCore;
import cn.org.supay.core.channel.aggregate.data.*;
import cn.org.supay.core.channel.alipay.data.AliPayPageResponse;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayPayType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

/**
 * <b>Application name：</b> AggregateDemoController.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月30日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Controller
@RequestMapping("/aggregate")
public class AggregateDemoController {

    /**
     * 跳到支付宝支付页面
     * 针对实时支付,即时付款
     * @param price       金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toAliPagePay.html", produces = "text/html;charset=UTF-8")
    public HttpEntity<String> toAliPagePay(BigDecimal price, String appId) {
        //及时收款
        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文参数
        SupayContext cxt = SupayPagePayRequest.builder()
                .tradeNo(orderCode)
                .payType(SupayPayType.ALI_PAGE_PAY)
                .tradeName("测试网页支付")
                .amount(price)
                .returnUrl("http://taobao.com")
                .build()
                .toContext(appId, false);

        // 调用支付接口
        cxt = SupayCore.pay(cxt);

        String result = ((SupayPagePayResponse)cxt.getResponse()).getRedirectPageBody();

        return new HttpEntity<>(result);
    }

    /**
     * 跳到支付宝支付页面
     * 针对实时支付,即时付款
     * @param price       金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toAliAppPay.html", produces = "text/html;charset=UTF-8")
    public HttpEntity<String> toAliAppPay(BigDecimal price, String appId) {
        //及时收款
        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文参数
        SupayContext cxt = SupayAppPayRequest.builder()
                .tradeNo(orderCode)
                .payType(SupayPayType.ALI_APP_PAY)
                .tradeName("测试APP支付")
                .amount(price)
                .returnUrl("http://taobao.com")
                .build()
                .toContext(appId, false);

        // 调用支付接口
        cxt = SupayCore.pay(cxt);

        String result = ((SupayAppPayResponse)cxt.getResponse()).getRedirectPageBody();
        return new HttpEntity<>(result);
    }

    /**
     * 跳到支付宝支付页面
     * 针对实时支付,即时付款
     * @param price       金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toAliWapPay.html", produces = "text/html;charset=UTF-8")
    public HttpEntity<String> toAliWapPay(BigDecimal price, String appId) {
        //及时收款
        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文参数
        SupayContext cxt = SupayH5PayRequest.builder()
                .tradeNo(orderCode)
                .payType(SupayPayType.ALI_WAP_PAY)
                .tradeName("测试APP支付")
                .amount(price)
                .returnUrl("http://taobao.com")
                .build()
                .toContext(appId, false);

        // 调用支付接口
        cxt = SupayCore.pay(cxt);

        String result = ((SupayH5PayResponse)cxt.getResponse()).getRedirectPageBody();
        return new HttpEntity<>(result);
    }
}