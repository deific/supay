/*******************************************************************************
 * @(#)SupayChannelConfig.java 2020年05月15日 23:05
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.config;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.org.supay.core.channel.PayChannelService;
import cn.org.supay.core.channel.notify.NotifyCallbackHandler;
import lombok.extern.slf4j.Slf4j;

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

    public static void init() {
        initPayChannelService();
        initNotifyHandler();
    }

    /**
     * 初始化
     */
    public static void initPayChannelService() {
        Set<Class<?>> channelServiceSet = ClassUtil.scanPackageBySuper("cn.org.supay.core.channel", PayChannelService.class);
        if (ObjectUtil.isNotEmpty(channelServiceSet)) {
            channelServiceSet.forEach(aClass -> {
                try {
                    if (!ClassUtil.isAbstract(aClass)) {
                        PayChannelService channelService = (PayChannelService) aClass.newInstance();
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
     * 初始化渠道异步通知处理器
     */
    public static void initNotifyHandler() {
        Set<Class<?>> notifyHandlerSet = ClassUtil.scanPackageBySuper("cn.org.supay.core.channel", NotifyCallbackHandler.class);
        if (ObjectUtil.isNotEmpty(notifyHandlerSet)) {
            notifyHandlerSet.forEach(aClass -> {
                try {
                    if (!ClassUtil.isAbstract(aClass)) {
                        NotifyCallbackHandler notifyHandler = (NotifyCallbackHandler) aClass.newInstance();
                        SupayCoreConfig.registerNotifyHandler(notifyHandler.getSupportType(), notifyHandler);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    log.error("初始化渠道异步通知处理器异常：", e);
                }
            });
        }
    }
}