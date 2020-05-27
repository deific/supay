/*******************************************************************************
 * @(#)AliPayChannelService.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay;

import cn.org.supay.core.channel.BasePayChannelService;
import cn.org.supay.core.channel.alipay.sdk.Factory;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;
import com.alipay.easysdk.kernel.BaseClient;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import lombok.extern.slf4j.Slf4j;

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
    /** 阿里支付客户端 */
    private Map<String, Factory> factoryMap = new HashMap<>();

    @Override
    public String getPayServiceName() {
        return "aliPayChannelService";
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        try {
            // 简易支付

            AlipayTradeCreateResponse response = Factory.Payment.Common(ctx.getChannelConfig().getAppId())
                    .create("", "null", "null", "null");
            if ("10000".equals(response.code)) {
                log.debug("支付成功");
            }
        } catch (Exception e) {

        }

        return null;
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
        // 请更换为您的应用公钥证书文件路径
        config.merchantCertPath = "/home/foo/appCertPublicKey_2019051064521003.crt";
        // 请更换为您的支付宝公钥证书文件路径
        config.alipayCertPath = "/home/foo/alipayCertPublicKey_RSA2.crt";
        // 请更换为您的支付宝根证书文件路径
        config.alipayRootCertPath = "/home/foo/alipayRootCert.crt";
        // 请更换为您的PKCS8格式的应用私钥
        config.merchantPrivateKey = "MIIEvQIBADANB ... ...";

        // 如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
        // config.alipayPublicKey = "MIIBIjANBg...";
        Factory.setOptions(config);
    }
}