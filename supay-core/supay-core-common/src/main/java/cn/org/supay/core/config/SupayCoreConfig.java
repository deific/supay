/*******************************************************************************
 * @(#)SupayServerConfig.java 2020年05月15日 23:05
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.config;

import cn.hutool.core.collection.ListUtil;
import cn.org.supay.core.channel.ChannelPayService;
import cn.org.supay.core.channel.converter.ChannelDataConverter;
import cn.org.supay.core.channel.notify.ChannelNotifyHandler;
import cn.org.supay.core.channel.proxy.ChannelPayProxy;
import cn.org.supay.core.channel.proxy.ProxyFactory;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.filter.ChannelStatsFilter;
import cn.org.supay.core.filter.SupayFilter;
import cn.org.supay.core.stats.SupayStats;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class SupayCoreConfig {

    /** 支付渠道参数配置列表 */
    private static Map<String, SupayChannelConfig> channelConfigMap = new HashMap<>();
    /** 支付渠道服务配置列表 */
    private static Map<SupayChannelType, ChannelPayService> channelServiceMap = new HashMap<>();
    /** 支付渠道异步通知处理列表 */
    private static Map<SupayChannelType, ChannelNotifyHandler> notifyHandlerMap = new HashMap<>();
    /** 参数转换器 */
    private static Map<SupayChannelType, ChannelDataConverter> converterMap = new HashMap() {{
        put(null, new ChannelDataConverter() {});
    }};
    /** 全局过滤器 */
    private static List<SupayFilter> filters = new ArrayList<>();
    /** 全局监控统计器 */
    private static SupayStats stats = new SupayStats();
    /** 是否启用全局监控统计 */
    private static boolean enableStats = true;

    /**
     * 注册渠道支付参数
     */
    public static void registerPayConfig(String appId, SupayChannelConfig channelConfig) {
        log.debug("[注册] 注册渠道支付参数：appId={} config={}", appId, channelConfig.getClass().getName());
        channelConfigMap.put(appId, channelConfig);
    }

    /**
     *
     * @param channelType 渠道类型
     * @param channelService 渠道服务
     * @param isFinal 是否最终支付渠道
     * @param filters 过滤器
     */
    public static void registerPayService(SupayChannelType channelType, ChannelPayService channelService, boolean isFinal, SupayFilter... filters) {
        log.debug("[注册] 注册渠道支付服务：channelType={} channelService={}", channelType, channelService.getClass().getName());
        // 通过代理服务注册
        ChannelPayProxy proxy = ProxyFactory.getProxy(channelService);
        // 如果开启了
        if (enableStats && isFinal) {
            proxy.addFilter(new ChannelStatsFilter());
        }
        // 如果全局filter存在
        if (!SupayCoreConfig.filters.isEmpty()) {
            proxy.addFilter(SupayCoreConfig.filters);
        }

        // 渠道过滤器
        if (filters != null) {
            proxy.addFilter(filters);
        }
        ChannelPayService proxyService = proxy.getProxyService();
        channelServiceMap.put(channelType, proxyService);
    }

    /**
     * 注册渠道支付服务
     * @param channelType 渠道类型
     * @param channelService 渠道服务实例
     */
    public static void registerPayService(SupayChannelType channelType, ChannelPayService channelService, SupayFilter... filters) {
        registerPayService(channelType, channelService, true, filters);
    }

    /**
     * 注册渠道异步通知处理器
     * @param channelType 渠道类型
     * @param notifyHandler 渠道服务实例
     */
    public static void registerNotifyHandler(SupayChannelType channelType, ChannelNotifyHandler notifyHandler) {
        log.debug("[注册] 注册渠道异步通知处理器：channelType={} notifyHandler={}", channelType, notifyHandler.getClass().getName());
        notifyHandlerMap.put(channelType, notifyHandler);
    }

    /**
     * 注册渠道接口参数转换器
     * @param channelType 渠道类型
     * @param converter 转换器
     */
    public static void registerParamConverter(SupayChannelType channelType, ChannelDataConverter converter) {
        log.debug("[注册] 注册渠道接口参数转换器：channelType={} converter={}", channelType, converter.getClass().getName());
        converterMap.put(channelType, converter);
    }

    /**
     * 注册全局filter,对所有渠道服务起作用
     * @param filter
     */
    public static void registerFilter(SupayFilter... filter) {
        filters.addAll(ListUtil.toList(filter));
    }

    /**
     * 获取支付参数
     * @param appId appId
     * @return 支付参数对象
     */
    public static SupayChannelConfig getChannelConfig(String appId) {
        return channelConfigMap.get(appId);
    }

    /**
     * 获取支付服务
     * @param channelType
     * @return
     */
    public static ChannelPayService getPayChannelService(SupayChannelType channelType) {
        log.debug("[初始化]选择渠道服务：{}", channelType);
        ChannelPayService proxyService = channelServiceMap.get(channelType);
        if(proxyService == null) {
            log.error("[准备]渠道服务不存在或未注册，转调模拟服务");
            proxyService = new ChannelPayService() {
                @Override
                public SupayChannelType getSupportType() {
                    return SupayChannelType.MOCK;
                }
            };
        }
        return proxyService;
    }

    /**
     * 获取渠道异步通知处理器
     * @param channelType
     * @return
     */
    public static ChannelNotifyHandler getNotifyHandler(SupayChannelType channelType) {
        return notifyHandlerMap.get(channelType);
    }

    /**
     * 获取参数转换器
     * @param channelType
     * @return
     */
    public static ChannelDataConverter getApiParamConverter(SupayChannelType channelType) {
        ChannelDataConverter converter = converterMap.get(channelType);
        converter = converter == null?converterMap.get(null):converter;
        return converter;
    }

    /**
     * 获取全局统计器
     * @return
     */
    public static SupayStats getSupayStats() {
        return stats;
    }

    public static boolean isEnableStats() {
        return enableStats;
    }
}