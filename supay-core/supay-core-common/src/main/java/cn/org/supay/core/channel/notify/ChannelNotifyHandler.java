/*******************************************************************************
 * @(#)NotifyCallbackHandler.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.notify;

/**
 * <b>Application name：</b> ChannelNotifyHandler.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年06月06日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface ChannelNotifyHandler {
    /**
     * 渠道通知回调处理
     * 根据
     * @see ChannelNotifyHandler#getSupportType 返回值，转换通知数据为对应渠道数据对象，传入该方法进行处理
     * @param notifyType 通知类型
     * @param notifyData 对应类型的通知数据封装对象 ChannelNotifyData子类实现
     * @return true 通知处理成功 false 通知处理失败
     */
    boolean handleNotify(ChannelNotifyType notifyType, ChannelNotifyData notifyData);
}