/*******************************************************************************
 * @(#)WxPayChannelService.java 2020年05月16日 09:37
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.spay.core.channel.BasePayChannelService;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderRequest;
import com.spay.core.channel.wx.data.WxPayUnifiedOrderResponse;
import com.spay.core.context.SpayContext;
import com.spay.core.converter.SpayConverter;
import com.spay.core.data.SpayRequest;
import com.spay.core.data.SpayResponse;

/**
 * <b>Application name：</b> WxPayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class WxPayConverter implements SpayConverter {

    @Override
    public String convert(Object request) {
        return XmlUtil.mapToXmlStr(BeanUtil.beanToMap(request));
    }

    /**
     * 响应转换为对象
     * @param body
     * @param targetClass
     * @return
     */
    @Override
    public <T> T convert(String body, Class<T> targetClass) {
        return XmlUtil.readObjectFromXml(body);
    }
}