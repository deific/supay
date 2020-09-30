/*******************************************************************************
 * @(#)AliSupayCoreDemo.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.demo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import cn.org.supay.core.channel.aggregate.Supay;
import cn.org.supay.core.channel.aggregate.data.SupayPayQueryResponse;
import cn.org.supay.core.channel.aggregate.data.SupayRefundQueryResponse;
import cn.org.supay.core.channel.alipay.data.AliPayPageRequest;
import cn.org.supay.core.channel.alipay.data.AliPayQueryRequest;
import cn.org.supay.core.channel.alipay.data.AliPayQueryResponse;
import cn.org.supay.core.channel.alipay.filter.AliPayFilter;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.SupayCore;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * <b>Application name：</b> AliSupayCoreDemo.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月30日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class AliSupayCoreDemo {

    private static Props props;
    private static  SupayChannelConfig channelConfig;

    // 初始化
    static {
        props = new Props("config/my-ali-pay.conf");
        // 初始化配置
        channelConfig = SupayChannelConfig.builder()
                .rootSecretKey(props.getStr("ali.publicKey"))
                .appId(props.getStr("ali.appId")).appSecret(props.getStr("ali.appSecret")).appName("支付宝应用-支付")
                .mchId(props.getStr("ali.mchId")).mchName("支付宝商户").mchSecretKey(props.getStr("ali.mchSecretKey"))
                .channelType(SupayChannelType.ALIPAY).apiBaseUrl("https://openapi.alipaydev.com/gateway.do")
                .build()
                .register();

    }

    public static void main(String[] args) {

//        AliSupayCoreDemo.testScanPay();
        AliSupayCoreDemo.testAppPay();
//        AliSupayCoreDemo.testMpPay();
        AliSupayCoreDemo.testH5Pay();
//        AliSupayCoreDemo.testFacePay();
//        AliSupayCoreDemo.testRefund();
        AliSupayCoreDemo.testPayQuery();
        AliSupayCoreDemo.testRefundQuery();

        log.debug("统计：{}", JSON.toJSONString(SupayCoreConfig.getSupayStats()));
    }

    public static void test() {
        String orderCode = IdUtil.fastSimpleUUID();

        // 构建支付上下文
        SupayContext cxt = AliPayPageRequest.builder()
                .outTradeNo(orderCode)
                .payType(SupayPayType.ALI_PAGE_PAY)
                .subject("测试网页支付")
                .totalAmount("1")
                .returnUrl("http://taobao.com")
                .build().toContext(channelConfig.getAppId(), false);

        // 本地模拟支付
        cxt.setLocalMock(true);
        // 调用支付接口
        cxt = (SupayContext) SupayCore.pay(cxt);

        log.debug("交易状态：{} 信息：{} 耗时：{}ms 接口响应数据：{}", cxt.hasError(),
                cxt.getMsg(), cxt.duration(), JSONUtil.toJsonStr(cxt.getResponse()));

        // 查询支付订单
        AliPayQueryRequest queryRequest = AliPayQueryRequest.builder().outTradeNo(orderCode).build();
        cxt = SupayContext.buildContext(channelConfig, queryRequest, false);
        cxt.setLocalMock(true);
        // 调用支付接口
        cxt = (SupayContext) SupayCore.payQuery(cxt);
        log.debug("交易状态：{} 信息：{} 耗时：{}ms 接口响应数据：{}", cxt.hasError(), cxt.getMsg(), cxt.duration(), ((AliPayQueryResponse)cxt.getResponse()));
    }

    public static void testScanPay() {
        String orderCode = IdUtil.fastSimpleUUID();
        String qrCodeUrl = Supay.scanPay(channelConfig.getAppId(), "扫码测试支付", orderCode, new BigDecimal(0.01), "https://www.spay.org.cn/notify");
        log.debug("app支付内容：{}", qrCodeUrl);
    }

    public static void testAppPay() {
        String orderCode = IdUtil.fastSimpleUUID();
        String appParamJson = Supay.appPay(channelConfig.getAppId(), "app测试支付", orderCode, new BigDecimal(0.01), "https://www.spay.org.cn/notify");
        log.debug("二维码支付内容：{}", appParamJson);
    }

    public static void testMpPay() {
        String orderCode = IdUtil.fastSimpleUUID();
        String qrCodeUrl = Supay.mpPay(channelConfig.getAppId(), "公众号测试支付", orderCode, new BigDecimal(0.01), "https://www.spay.org.cn/notify");
        log.debug("app支付内容：{}", qrCodeUrl);
    }

    public static void testH5Pay() {
        String orderCode = IdUtil.fastSimpleUUID();
        String qrCodeUrl = Supay.h5Pay(channelConfig.getAppId(), "h5网页测试支付", orderCode, new BigDecimal(0.01), "https://www.spay.org.cn/notify");
        log.debug("app支付内容：{}", qrCodeUrl);
    }

    public static void testFacePay() {
        String orderCode = IdUtil.fastSimpleUUID();
        String qrCodeUrl = Supay.facePay(channelConfig.getAppId(), "面对面测试支付", orderCode, new BigDecimal(0.01), "https://www.spay.org.cn/notify");
        log.debug("app支付内容：{}", qrCodeUrl);
    }


    public static void testRefund() {
        String orderCode = IdUtil.fastSimpleUUID();
        String refundCode = IdUtil.fastSimpleUUID();
        Supay.refund(channelConfig.getAppId(), orderCode, refundCode, new BigDecimal("0.01"), new BigDecimal("0.01"), "https://www.spay.org.cn/notify");
    }

    public static void testPayQuery() {
        String orderCode = IdUtil.fastSimpleUUID();
        String refundCode = IdUtil.fastSimpleUUID();
        SupayPayQueryResponse payQueryResponse = Supay.payQuery(channelConfig.getAppId(), orderCode);
    }

    private static void testRefundQuery() {
        String orderCode = IdUtil.fastSimpleUUID();
        String refundCode = IdUtil.fastSimpleUUID();
        SupayRefundQueryResponse refundQueryResponse = Supay.refundQuery(channelConfig.getAppId(), orderCode, refundCode);
    }
}