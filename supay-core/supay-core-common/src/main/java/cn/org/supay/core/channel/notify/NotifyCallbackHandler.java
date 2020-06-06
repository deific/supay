/*******************************************************************************
 * @(#)NotifyCallbackHandler.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.notify;

import cn.org.supay.core.channel.PayChannelService;
import cn.org.supay.core.channel.notify.NotifyData;
import cn.org.supay.core.enums.SupayChannelType;

/**
 * <b>Application name：</b> NotifyCallbackHandler.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年06月06日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface NotifyCallbackHandler {

    /**
     * 获取支持的渠道类型
     * @return
     */
    SupayChannelType getSupportType();

    /**
     * 渠道通知回调处理
     * @param notifyData
     * @param service
     * @return
     */
    String handle(NotifyData notifyData, PayChannelService service);

}