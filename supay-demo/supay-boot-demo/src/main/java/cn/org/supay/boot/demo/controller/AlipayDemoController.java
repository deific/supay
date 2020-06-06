/*******************************************************************************
 * @(#)AlipayDemoController.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.boot.demo.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.org.supay.core.channel.alipay.AliPayChannelService;
import cn.org.supay.core.channel.alipay.data.AliPayPageRequest;
import cn.org.supay.core.channel.alipay.data.AliPayPageResponse;
import cn.org.supay.core.channel.alipay.data.AliPayQueryRequest;
import cn.org.supay.core.channel.alipay.data.AliPayQueryResponse;
import cn.org.supay.core.channel.alipay.filter.AliPayFilter;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.pay.SupayCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * <b>Application name：</b> AlipayDemoController.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月30日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Controller
@RequestMapping("/alipay")
public class AlipayDemoController {

    private static Props props;
    private static  SupayChannelConfig channelConfig;

    // 初始化
    static {
        props = new Props("./config/my-ali-pay.conf");
        // 初始化渠道参数配置
        channelConfig = SupayChannelConfig.builder()
                .rootSecretKey(props.getStr("ali.publicKey"))
                .appId(props.getStr("ali.appId")).appSecret(props.getStr("ali.appSecret")).appName("支付宝应用-支付")
                .mchId(props.getStr("ali.mchId")).mchName("支付宝商户").mchSecretKey(props.getStr("ali.mchSecretKey"))
                .channelType(SupayChannelType.ALIPAY).apiBaseUrl("https://openapi.alipaydev.com/gateway.do")
                .build()
                .addFilter(new AliPayFilter())
                .register()
        ;
    }


    /**
     * 跳到支付页面
     * 针对实时支付,即时付款
     *
     * @param price       金额
     * @return 跳到支付页面
     */
    @RequestMapping(value = "toPay.html", produces = "text/html;charset=UTF-8")
    public HttpEntity<String> toPay(BigDecimal price) {
        //及时收款
        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文参数
        SupayContext cxt = AliPayPageRequest.builder()
                .outTradeNo(orderCode)
//                .payType(SupayPayType.ALI_PAGE_PAY)
                .payType(SupayPayType.ALI_WAP_PAY)
                .subject("测试网页支付")
                .totalAmount(price.toString())
                .returnUrl("http://taobao.com")
                .build()
                .toContext(channelConfig.getAppId(), false);

        // 调用支付接口
        cxt = SupayCore.pay(cxt);

        String result = ((AliPayPageResponse)cxt.getResponse()).getBody();

        return new HttpEntity<>(result);
    }

    public static void main(String[] args) {


        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文
        // 构建支付上下文参数
        AliPayPageRequest request = AliPayPageRequest.builder()
                .outTradeNo(orderCode)
//                .payType(SupayPayType.ALI_PAGE_PAY)
                .payType(SupayPayType.ALI_WAP_PAY)
                .subject("测试网页支付")
                .totalAmount("1")
                .returnUrl("http://taobao.com")
                .build();

        // 构建微信支付上下文
        SupayContext cxt = SupayContext.buildContext(channelConfig, request, false);
        // 调用支付接口
        cxt = (SupayContext) SupayCore.pay(cxt);
        log.debug("交易状态：{} 信息：{} 耗时：{} 接口响应数据：{}", cxt.hasError(), cxt.getMsg(), cxt.duration(), JSONUtil.toJsonStr(cxt.getResponse()));

        // 查询支付订单
        AliPayQueryRequest queryRequest = AliPayQueryRequest.builder().outTradeNo(orderCode).build();
        cxt = SupayContext.buildContext(channelConfig, queryRequest, false);
        // 调用支付接口
        cxt = (SupayContext) SupayCore.queryPayOrder(cxt);
        log.debug("交易状态：{} 信息：{} 耗时：{} 接口响应数据：{}", cxt.hasError(), cxt.getMsg(), cxt.duration(), ((AliPayQueryResponse)cxt.getResponse()));

    }
}