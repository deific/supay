/*******************************************************************************
 * @(#)Request.java 2020年05月15日 22:44
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.data;

import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayConfig;
import cn.org.supay.core.context.SupayContext;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <b>Application name：</b> Request.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:44 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface Request {

    /**
     * 转换为上下文
     * @param appId
     * @param isSandbox
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> toContext(String appId, boolean isSandbox) {
        return SupayContext.buildContext(SupayConfig.getPayConfig(appId), this, isSandbox);
    }
    /**
     * 参数检查并签名
     * @param ctx
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> checkAndSign(SupayContext<? extends Request, ? extends Response> ctx) {
        return ctx;
    }


}