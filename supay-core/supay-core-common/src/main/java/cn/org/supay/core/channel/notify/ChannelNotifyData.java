/*******************************************************************************
 * @(#)NotifyCallbackHandler.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.notify;

import java.util.Map;

/**
 * <b>Application name：</b> ChannelNotifyData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年06月06日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface ChannelNotifyData {
    /**
     * 获取通知原始数据
     * @return 原始数据map
     */
    default Map getNotifyOriginData() {
        return null;
    }
}