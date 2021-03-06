/*******************************************************************************
 * @(#)JdkProxy.java 2020年09月26日 22:08
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.proxy;

import cn.org.supay.core.channel.ChannelPayService;

/**
 * <b>Application name：</b> JdkProxy.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年09月26日 22:08 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class ProxyFactory {

    /**
     * 获取代理类
     * @param targetService
     * @return
     */
    public static ChannelPayProxy getProxy(ChannelPayService targetService) {
        if (targetService.getClass().isInterface()) {
            return new JdkProxy(targetService);
        } else {
            return new CglibProxy(targetService);
        }
    }
}