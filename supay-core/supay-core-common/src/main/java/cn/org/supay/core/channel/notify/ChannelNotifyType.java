/*******************************************************************************
 * @(#)WxPayNotifyData.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.notify;

import cn.org.supay.core.enums.SupayChannelType;

/**
 * <b>Application name：</b> WxNotifyType.java <br>
 * <b>Application describing： </b>
 * 异步通知类型
 * 具体类型由各渠道子枚举实现定义
 * <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年06月06日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface ChannelNotifyType {
    /**
     * 渠道类型
     * @return
     */
    SupayChannelType channelType();
}