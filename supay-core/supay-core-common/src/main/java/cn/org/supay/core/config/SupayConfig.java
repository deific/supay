/*******************************************************************************
 * @(#)SupayServerConfig.java 2020年05月15日 23:05
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.config;

import cn.org.supay.core.channel.PayChannelService;
import cn.org.supay.core.channel.notify.NotifyCallbackHandler;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.converter.SupayConverter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> SupayServerConfig.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 23:05 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Data
public class SupayConfig {

    /** 支付渠道参数配置列表 */
    private static Map<String, SupayChannelConfig> channelConfigMap = new HashMap<>();
    /** 支付渠道服务配置列表 */
    private static Map<SupayChannelType, PayChannelService> channelServiceMap = new HashMap<>();
    /** 支付渠道异步通知处理列表 */
    private static Map<SupayChannelType, NotifyCallbackHandler> notifyHandlerMap = new HashMap<>();
    /** 参数转换器 */
    private static Map<SupayChannelType, SupayConverter> converterMap = new HashMap() {{
        put(null, new SupayConverter() {});
    }};

    /**
     * 注册渠道支付参数
     */
    public static void registerPayConfig(String appId, SupayChannelConfig channelConfig) {
        log.debug("[注册] 注册渠道支付参数：appId={} config={}", appId, channelConfig.getClass().getName());
        channelConfigMap.put(appId, channelConfig);
    }

    /**
     * 注册渠道支付服务
     * @param channelType 渠道类型
     * @param channelService 渠道服务实例
     */
    public static void registerPayService(SupayChannelType channelType, PayChannelService channelService) {
        log.debug("[注册] 注册渠道支付服务：channelType={} channelService={}", channelType, channelService.getClass().getName());
        channelServiceMap.put(channelType, channelService);
    }

    /**
     * 注册渠道异步通知处理器
     * @param channelType 渠道类型
     * @param notifyHandler 渠道服务实例
     */
    public static void registerNotifyHandler(SupayChannelType channelType, NotifyCallbackHandler notifyHandler) {
        log.debug("[注册] 注册渠道异步通知处理器：channelType={} notifyHandler={}", channelType, notifyHandler.getClass().getName());
        notifyHandlerMap.put(channelType, notifyHandler);
    }

    /**
     * 注册渠道接口参数转换器
     * @param channelType 渠道类型
     * @param converter 转换器
     */
    public static void registerParamConverter(SupayChannelType channelType, SupayConverter converter) {
        log.debug("[注册] 注册渠道接口参数转换器：channelType={} converter={}", channelType, converter.getClass().getName());
        converterMap.put(channelType, converter);
    }
    /**
     * 获取支付参数
     * @param appId appId
     * @return 支付参数对象
     */
    public static SupayChannelConfig getPayConfig(String appId) {
        return channelConfigMap.get(appId);
    }

    /**
     * 获取支付服务
     * @param channelType
     * @return
     */
    public static PayChannelService getPayService(SupayChannelType channelType) {
        return channelServiceMap.get(channelType);
    }

    /**
     * 获取渠道异步通知处理器
     * @param channelType
     * @return
     */
    public static NotifyCallbackHandler getNotifyHandler(SupayChannelType channelType) {
        return notifyHandlerMap.get(channelType);
    }

    /**
     * 获取参数转换器
     * @param channelType
     * @return
     */
    public static SupayConverter getApiParamConverter(SupayChannelType channelType) {
        SupayConverter converter = converterMap.get(channelType);
        converter = converter == null?converterMap.get(null):converter;
        return converter;
    }

}