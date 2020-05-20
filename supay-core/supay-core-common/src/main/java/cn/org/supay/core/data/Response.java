/*******************************************************************************
 * @(#)Response.java 2020年05月15日 22:40
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.data;

import cn.org.supay.core.context.SupayContext;
import lombok.Data;

/**
 * <b>Application name：</b> Response.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
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
    public SupayContext<? extends Request, ? extends Response> checkResult(SupayContext<? extends Request, ? extends Response> ctx) {
        return ctx;
    }
}