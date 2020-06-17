/*******************************************************************************
 * @(#)SupayConverter.java 2020年05月16日 11:00
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.converter;

import cn.hutool.json.JSONUtil;
import cn.org.supay.core.channel.data.Response;

/**
 * <b>Application name：</b> ChannelDataConverter.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 11:00 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface ChannelDataConverter {

    /**
     * 请求对象转换
     * @param obj 对象
     * @return 转换后字符串
     */
    default String convert(Object obj) {
        return JSONUtil.toJsonStr(obj);
    }

    /**
     * 响应转换为对象
     * @param body 数据字符串
     * @param targetClass 转换目标对象类型
     * @return 目标对象实例
     */
    default Response convert(String body, Class<? extends Response> targetClass) {
        return JSONUtil.toBean(body, targetClass);
    }
}