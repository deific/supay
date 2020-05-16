/*******************************************************************************
 * @(#)SpayRequestConverter.java 2020年05月16日 11:00
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.converter;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.spay.core.data.SpayRequest;
import com.spay.core.data.SpayResponse;

/**
 * <b>Application name：</b> SpayRequestConverter.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 11:00 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface SpayConverter {

    /**
     * 请求对象转换
     * @param request
     * @return
     */
    default String convert(Object request) {
        return JSONUtil.toJsonStr(request);
    }

    /**
     * 响应转换为对象
     * @param body
     * @param targetClass
     * @return
     */
    default <T> T convert(String body, Class<T> targetClass) {
        return JSONUtil.toBean(body, targetClass);
    }
}