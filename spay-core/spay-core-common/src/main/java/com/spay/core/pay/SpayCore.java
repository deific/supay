/*******************************************************************************
 * @(#)SpayCore.java 2020年05月16日 08:49
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.pay;

import com.spay.core.channel.PayChannelService;
import com.spay.core.config.SpayChannelConfig;
import com.spay.core.config.SpayConfig;
import com.spay.core.context.SpayContext;
import com.spay.core.data.SpayRequest;
import com.spay.core.data.SpayResponse;

/**
 * <b>Application name：</b> SpayCore.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 08:49 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class SpayCore {

    /** 私有构造函数 */
    private SpayCore() {}

    /**
     * 直接支付
     * @param spayContext 支付上下文
     * @return 支付上下文
     */
    public static  <T extends SpayContext> T pay(SpayContext<? extends SpayRequest, ? extends SpayResponse> spayContext) {
        SpayChannelConfig channelConfig = spayContext.getChannelConfig();
        if (channelConfig == null) {
            return SpayContext.fail(spayContext, "请配置支付渠道参数");
        }
        PayChannelService payService = SpayConfig.getPayService(channelConfig.getChannelType());
        if (payService != null) {
            return (T)payService.pay(spayContext);
        } else {
            return SpayContext.fail(spayContext, "不支持该渠道支付");
        }
    }
}