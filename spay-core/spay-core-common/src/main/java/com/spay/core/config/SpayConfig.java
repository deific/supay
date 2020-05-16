/*******************************************************************************
 * @(#)SpayServerConfig.java 2020年05月15日 23:05
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.config;

import com.spay.core.channel.PayChannelService;
import com.spay.core.converter.SpayConverter;
import com.spay.core.enums.SpayChannelType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import static com.spay.core.enums.SpayChannelType.WECHAT;

/**
 * <b>Application name：</b> SpayServerConfig.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 23:05 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Data
public class SpayConfig {

    /** 支付渠道参数配置列表 */
    private static Map<String, SpayChannelConfig> channelConfigMap = new HashMap<>();
    /** 支付渠道服务配置列表 */
    private static Map<SpayChannelType, PayChannelService> channelServiceMap = new HashMap<>();
    /** 参数转换器 */
    private static Map<SpayChannelType, SpayConverter> converterMap = new HashMap() {{
        put(null, new SpayConverter() {});
    }};

    /**
     * 注册渠道支付参数
     */
    public static void registerPayConfig(String appId, SpayChannelConfig channelConfig) {
        log.debug("[注册] 注册渠道支付参数：appId={} config={}", appId, channelConfig.getClass().getName());
        channelConfigMap.put(appId, channelConfig);
    }

    /**
     * 注册渠道支付服务
     * @param channelType 渠道类型
     * @param channelService 渠道服务实例
     */
    public static void registerPayService(SpayChannelType channelType, PayChannelService channelService) {
        log.debug("[注册] 注册渠道支付服务：channelType={} channelService={}", channelType, channelService.getClass().getName());
        channelServiceMap.put(channelType, channelService);
    }

    /**
     * 注册渠道接口参数转换器
     * @param channelType 渠道类型
     * @param converter 转换器
     */
    public static void registerParamConverter(SpayChannelType channelType, SpayConverter converter) {
        log.debug("[注册] 注册渠道接口参数转换器：channelType={} converter={}", channelType, converter.getClass().getName());
        converterMap.put(channelType, converter);
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
    public static PayChannelService getPayService(SpayChannelType channelType) {
        return channelServiceMap.get(channelType);
    }

    /**
     * 获取参数转换器
     * @param channelType
     * @return
     */
    public static SpayConverter getApiParamConverter(SpayChannelType channelType) {
        SpayConverter converter = converterMap.get(channelType);
        converter = converter == null?converterMap.get(null):converter;
        return converter;
    }

}