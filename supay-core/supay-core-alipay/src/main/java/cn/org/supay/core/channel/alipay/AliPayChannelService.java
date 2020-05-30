/*******************************************************************************
 * @(#)AliPayChannelService.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay;

import cn.org.supay.core.channel.BasePayChannelService;
import cn.org.supay.core.channel.alipay.data.AliPayBaseRequest;
import cn.org.supay.core.channel.alipay.data.AliPayPageRequest;
import cn.org.supay.core.channel.alipay.data.AliPayPageResponse;
import cn.org.supay.core.channel.alipay.data.AlipayBaseResponse;
import cn.org.supay.core.channel.alipay.sdk.Factory;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;
import cn.org.supay.core.enums.SupayPayType;
import com.alipay.easysdk.kernel.BaseClient;
import com.alipay.easysdk.payment.app.models.AlipayTradeAppPayResponse;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> AliPayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月25日 19:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class AliPayChannelService implements BasePayChannelService {

    @Override
    public String getPayServiceName() {
        return "aliPayChannelService";
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        // 检查并转换类型
        SupayContext<AliPayBaseRequest, AlipayBaseResponse> thisCtx = checkAndConvertType(ctx,
                AliPayBaseRequest.class, AlipayBaseResponse.class);
        if (thisCtx.hasError()) {
            return thisCtx;
        }
        try {
            // 初始化
            init(ctx.getChannelConfig());
            // 简易支付
            SupayPayType payType = thisCtx.getRequest().getPayType();
            switch (payType) {
                case ALI_PAGE_PAY:
                    AliPayPageRequest request = (AliPayPageRequest) thisCtx.getRequest();
                    AlipayTradePagePayResponse response = Factory.Payment.Page(ctx.getChannelConfig().getAppId())
                            .pay(request.getSubject(), request.getOutTradeNo(), request.getTotalAmount(), request.getReturnUrl());
                    AliPayPageResponse pageResponse = new AliPayPageResponse();
                    pageResponse.setBody(response.body);
                    thisCtx.setResponse(pageResponse);
                    return thisCtx;
                case ALI_APP_PAY:
                    AliPayBaseRequest appRequest = thisCtx.getRequest();
                    AlipayTradeAppPayResponse appResponse = Factory.Payment.App(ctx.getChannelConfig().getAppId())
                            .pay(appRequest.getSubject(), appRequest.getOutTradeNo(), appRequest.getTotalAmount());
                    AlipayBaseResponse appPayResponse = new AliPayPageResponse();
                    appPayResponse.setBody(appResponse.body);
                    thisCtx.setResponse(appPayResponse);
                    return thisCtx;
            }
        } catch (Exception e) {
            thisCtx.fail("调用阿里支付接口异常:" + e.getMessage());
            log.error("调用阿里支付接口异常",e);
        }
        return thisCtx;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> confirm(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryTradeInfo(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> sendRedPackage(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }

    /**
     * 获取阿里支付客户端
     * @param channelConfig
     * @return
     */
    private void init(SupayChannelConfig channelConfig) {
        BaseClient.Config config = new BaseClient.Config();
        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";

        // 请更换为您的AppId
        config.appId = channelConfig.getAppId();
//        // 请更换为您的应用公钥证书文件路径
//        config.merchantCertPath = "/home/foo/appCertPublicKey_2019051064521003.crt";
//        // 请更换为您的支付宝公钥证书文件路径
//        config.alipayCertPath = "/home/foo/alipayCertPublicKey_RSA2.crt";
//        // 请更换为您的支付宝根证书文件路径
//        config.alipayRootCertPath = "/home/foo/alipayRootCert.crt";
        // 请更换为您的PKCS8格式的应用私钥
        config.merchantPrivateKey = channelConfig.getMchSecretKey();

        // 如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        // config.alipayPublicKey = "MIIBIjANBg...";
        Factory.setOptions(config);
    }
}