/*******************************************************************************
 * @(#)BasePayChannelService.java 2020年05月16日 10:16
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel;

import com.spay.core.config.SpayChannelConfig;
import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;

/**
 * <b>Application name：</b> BasePayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 10:16 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface  BasePayChannelService extends PayChannelService {

    /**
     * 获取请求url地址
     * @param config
     * @param wxApiType
     * @return 返回渠道方请求地址
     */
    default String getReqUrl(SpayChannelConfig config, ChannelApiType wxApiType) {
        return config.getApiBaseUrl().concat(wxApiType.getUrl());
    }
    /**
     * 检查上下文参数类型
     * @param ctx 上下文
     * @param r 请求参数类型
     * @param s 响应参数类型
     * @return 返回转换后的指定类型对象
     */
    default  <T> T checkAndConvertType(SpayContext<? extends Request, ? extends Response> ctx, Class<? extends Request> r, Class<? extends Response> s) {
        // 判断类型是否匹配
        boolean isMatch = (ctx.getRequest() != null && !ctx.getRequest().getClass().isInstance(r))
                || (ctx.getResponse() != null && !ctx.getResponse().getClass().isInstance(s));
        if (!isMatch) {
            ctx.fail("请求参数类型或响应类型与当前渠道要求类型不匹配");
        }
        // 参数检查
        return (T)ctx;
    }

}