/*******************************************************************************
 * @(#)WxPayChannelService.java 2020年05月16日 09:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.convert;

import cn.hutool.core.util.XmlUtil;
import cn.org.supay.core.channel.converter.ChannelDataConverter;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.utils.BeanUtils;

/**
 * <b>Application name：</b> WxChannelPayService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class WxPayConverter implements ChannelDataConverter {

    @Override
    public String convert(Object request) {
        return XmlUtil.mapToXmlStr(BeanUtils.xmlBean2Map(request), false);
    }

    /**
     * 响应转换为对象
     * @param body 转换字符串
     * @param targetClass 目标对象类型
     * @return
     */
    @Override
    public Response convert(String body, Class<? extends Response> targetClass) {
        return BeanUtils.map2XmlBean(XmlUtil.xmlToMap(body), targetClass);
    }
}