/*******************************************************************************
 * @(#)SupayUtils.java 2020年05月16日 15:30
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.utils;

import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.context.SupayContext;

/**
 * <b>Application name：</b> SupayUtils.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 15:30 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class SupayUtils {

    /**
     * 检查上下文参数类型
     * @param ctx 上下文
     * @param r 请求参数类型
     * @param s 响应参数类型
     * @return 返回转换后的指定类型对象
     */
    public static  <T> T checkAndConvertType(SupayContext<? extends Request, ? extends Response> ctx, Class<? extends Request> r, Class<? extends Response> s) {
        // 判断类型是否匹配
        boolean isMatch = (ctx.getRequest() != null && r.isInstance(ctx.getRequest()));
        if (!isMatch && ctx.getRequest() != null) {
            ctx.fail("请求类型与当前渠道要求类型不匹配，要求类型：" + r.getName() + " 请求类型：" + ctx.getRequest().getClass());
            return (T)ctx;
        }
        isMatch = ctx.getResponse() != null && s.isInstance(ctx.getResponse());
        if (!isMatch && ctx.getResponse() != null) {
            ctx.fail("响应类型与当前渠道要求类型不匹配，要求类型：" + s.getName() + " 响应类型：" + ctx.getResponse().getClass());
            return (T)ctx;
        }
        // 参数检查
        return (T)ctx;
    }
}