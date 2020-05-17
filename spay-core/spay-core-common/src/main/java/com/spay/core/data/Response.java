/*******************************************************************************
 * @(#)SPayData.java 2020年05月15日 22:40
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.data;

import com.spay.core.context.SpayContext;
import lombok.Data;

/**
 * <b>Application name：</b> SPayData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 22:40 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class Response<T> {

    /**
     * 验证响应结果
     * @param ctx
     * @return
     */
    public SpayContext<? extends Request, ? extends Response> checkResult(SpayContext<? extends Request, ? extends Response> ctx) {
        return ctx;
    }
}