/*******************************************************************************
 * @(#)SupayConfigurer.java 2020年05月30日 09:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.boot.starter;

import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayChannelConfigLoader;
import cn.org.supay.core.config.SupayConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <b>Application name：</b> SupayConfigurer.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月30日 09:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Configuration
public class SupayConfigurer {

    @Resource
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        log.debug("[自动初始化]");
        SupayConfiguration.init();

        // 扫描渠道参数加载器接口实现，获取渠道参数
        Map<String, SupayChannelConfigLoader> configLoaderMap =context.getBeansOfType(SupayChannelConfigLoader.class);
        if (configLoaderMap != null && !configLoaderMap.isEmpty()) {
            configLoaderMap.forEach((key, configLoader) -> {
                List<SupayChannelConfig> channelConfigs = configLoader.loadChannelConfig();
                SupayConfiguration.loadChannelConfigs(channelConfigs);
            });
        }
    }
}