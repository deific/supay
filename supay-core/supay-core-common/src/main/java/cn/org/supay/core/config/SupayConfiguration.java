/*******************************************************************************
 * @(#)SupayChannelConfig.java 2020年05月15日 23:05
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.config;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.org.supay.core.channel.ChannelPayService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

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
public class SupayConfiguration {

    private static boolean isInit = false;

    /**
     * 初始化
     */
    public static synchronized void init() {
        if (!isInit) {
            log.debug("[初始化]执行初始化...");
            initPayChannelService();
        } else {
            log.debug("[初始化]已初始化，略过");
        }
    }

    /**
     * 初始化
     */
    public static void initPayChannelService() {
        Set<Class<?>> channelServiceSet = ClassUtil.scanPackageBySuper("cn.org.supay.core.channel", ChannelPayService.class);
        if (ObjectUtil.isNotEmpty(channelServiceSet)) {
            channelServiceSet.forEach(aClass -> {
                try {
                    if (!ClassUtil.isAbstract(aClass)) {
                        ChannelPayService channelService = (ChannelPayService) aClass.newInstance();
                        channelService.register();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("初始化渠道服务异常：", e);
                }
            });
        }
    }

    /**
     * 加载渠道配置参数
     * @param configs
     */
    public static void loadChannelConfig(SupayChannelConfig ...configs) {
        for (SupayChannelConfig config : configs) {
            SupayCoreConfig.registerChannelConfig(config.getAppId(), config);
        }
    }

    /**
     * 加载渠道配置参数
     * @param configs
     */
    public static void loadChannelConfigs(List<SupayChannelConfig> configs) {
        for (SupayChannelConfig config : configs) {
            SupayCoreConfig.registerChannelConfig(config.getAppId(), config);
        }
    }
}