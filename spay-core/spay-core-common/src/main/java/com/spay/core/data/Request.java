/*******************************************************************************
 * @(#)SPayRequest.java 2020年05月15日 22:44
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.data;

import com.spay.core.context.SpayContext;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <b>Application name：</b> SPayRequest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 22:44 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class Request implements Serializable {

    /**
     * 参数检查并签名
     * @param ctx
     * @return
     */
    public SpayContext<? extends Request, ? extends Response> checkAndSign(SpayContext<? extends Request, ? extends Response> ctx) {
        return ctx;
    }
}