/*******************************************************************************
 * @(#)AggregateContext.java 2020年05月16日 08:49
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.context;

import cn.hutool.core.util.IdUtil;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <b>Application name：</b> AggregateContext.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月31日 22:18 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class AggregateContext<R extends Request, S extends Response> extends SupayContext<R, S> {
    /** 转换后的原始请求 */
    private R originRequest;
    /** 转换后的原始响应 */
    private S originResponse;

    /**
     * 构建上下文
     * @param channelConfig
     * @param request
     * @param isSandBox
     * @return
     */
    public static SupayContext buildContext(SupayChannelConfig channelConfig, Request request, boolean isSandBox) {
        SupayContext cxt = AggregateContext.builder()
                .tradeId(IdUtil.fastUUID())
                .channelConfig(channelConfig)
                .isSandBox(isSandBox)
                .request(request)
                .originRequest(request)
                .isLocalMock(false)
                .success(true)
                .build();
        return cxt;
    }
}