/*******************************************************************************
 * @(#)SpayServerConfig.java 2020年05月15日 23:05
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.config;

import com.spay.core.channel.PayChannelService;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> SpayServerConfig.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 23:05 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class SpayConfig {

    /** 支付渠道参数配置列表 */
    private static Map<String, SpayChannelConfig> channelConfigMap = new HashMap<>();
    /** 支付渠道服务配置列表 */
    private static Map<String, PayChannelService> channelServiceMap = new HashMap<>();

    /**
     * 注册渠道支付参数
     */
    public static void registerPayConfig(String appId, SpayChannelConfig channelConfig) {
        channelConfigMap.put(appId, channelConfig);
    }

    /**
     * 注册
     * @param channelType 渠道类型
     * @param channelService 渠道服务实例
     */
    public static void registerPayService(String channelType, PayChannelService channelService) {
        channelServiceMap.put(channelType, channelService);
    }

    /**
     * 获取支付参数
     * @param appId appId
     * @return 支付参数对象
     */
    public static SpayChannelConfig getPayConfig(String appId) {
        return channelConfigMap.get(appId);
    }

    /**
     * 获取支付服务
     * @param channelType
     * @return
     */
    public static PayChannelService getPayService(String channelType) {
        return channelServiceMap.get(channelType);
    }
}