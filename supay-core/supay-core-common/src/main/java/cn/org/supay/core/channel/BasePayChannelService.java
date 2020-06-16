/*******************************************************************************
 * @(#)BasePayChannelService.java 2020年05月16日 10:16
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel;

import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;

/**
 * <b>Application name：</b> BasePayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 10:16 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface  BasePayChannelService extends PayChannelService {

    /**
     * 获取请求url地址
     * @param config
     * @param wxApiType
     * @param isSandBox
     * @return 返回渠道方请求地址
     */
    default String getReqUrl(SupayChannelConfig config, PayChannelApiType wxApiType, Boolean isSandBox) {
        return config.getApiBaseUrl().concat(wxApiType.getUrl());
    }


}