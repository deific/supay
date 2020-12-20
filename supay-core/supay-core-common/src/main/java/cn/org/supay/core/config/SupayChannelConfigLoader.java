/*******************************************************************************
 * @(#)SupayChannelConfigLoader.java 2020年05月15日 23:05
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.config;

import java.util.List;

/**
 * <b>Application name：</b> SupayChannelConfigLoader.java <br>
 * <b>Application describing： </b>
 * 渠道参数加载器，基于spring boot starter 引用时，会自动扫描该接口的所有的实现类，调用方法将渠道参数注册到内部缓存，以供使用
 * <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 23:05 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface SupayChannelConfigLoader {

    /**
     * 加载渠道配置参数
     * @return
     */
    List<SupayChannelConfig> loadChannelConfig();
}