/*******************************************************************************
 * @(#)AliPayFilter.java 2020年05月31日 22:18
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.filter;

import cn.org.supay.core.channel.alipay.sdk.Factory;
import cn.org.supay.core.channel.filter.FilterChain;
import cn.org.supay.core.channel.filter.SupayFilter;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import com.alipay.easysdk.kernel.BaseClient;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

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
public class AliPayFilter implements SupayFilter {
    @Override
    public SupayContext<? extends Request, ? extends Response> before(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        // 调用方法前初始化阿里支付客户端
        init(ctx.getChannelConfig());
        return chain.nextBefore(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> after(SupayContext<? extends Request, ? extends Response> ctx, FilterChain chain) {
        return chain.nextAfter(ctx);
    }

    /**
     * 获取阿里支付客户端
     *
     * @param channelConfig
     * @return
     */
    private void init(SupayChannelConfig channelConfig) {
        BaseClient.Config config = new BaseClient.Config();

        try {
            URL url = new URL(channelConfig.getApiBaseUrl());
            config.protocol = url.getProtocol();
            config.gatewayHost = url.getHost();
            config.signType = "RSA2";
            // 请更换为您的AppId
            config.appId = channelConfig.getAppId();
            // 请更换为您的PKCS8格式的应用私钥
            config.merchantPrivateKey = channelConfig.getMchSecretKey();

            //        // 请更换为您的应用公钥证书文件路径
            //        config.merchantCertPath = "/home/foo/appCertPublicKey_2019051064521003.crt";
            //        // 请更换为您的支付宝公钥证书文件路径
            //        config.alipayCertPath = "/home/foo/alipayCertPublicKey_RSA2.crt";
            //        // 请更换为您的支付宝根证书文件路径
            //        config.alipayRootCertPath = "/home/foo/alipayRootCert.crt";
            // 如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
            config.alipayPublicKey = channelConfig.getRootSecretKey();

            Factory.setOptions(config);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}