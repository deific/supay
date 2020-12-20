/*******************************************************************************
 * @(#)SupayContext.java 2020年05月15日 22:51
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.context;

import cn.org.supay.core.channel.notify.ChannelNotifyType;
import lombok.Data;

import java.util.Map;

/**
 * <b>Application name：</b> SupayNotifyContext.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:51 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class SupayNotifyContext {

    /** 通知类型，使用 */
    private ChannelNotifyType notifyType;
    /** 表单数据 各渠道通知数据位置不一样，有的通过form，有的是requestBody */
    private Map formParam;
    /** 请求体数据 各渠道通知数据位置不一样，有的通过form，有的是requestBody */
    private String bodyStr;

    /** 通知处理返回字符串 各渠道返回数据格式要求不一样 */
    private String retStr;

    /**
     * 构建通知上下文
     * @param notifyType
     * @param formParam 通知数据（form参数形式）
     * @return
     */
    public static SupayNotifyContext buildNotifyContext(ChannelNotifyType notifyType, Map formParam) {
        return buildNotifyContext(notifyType, formParam, null);
    }

    /**
     * 构建通知上下文
     * @param notifyType
     * @param bodyStr 通知数据（request body形式）
     * @return
     */
    public static SupayNotifyContext buildNotifyContext(ChannelNotifyType notifyType, String bodyStr) {
        return buildNotifyContext(notifyType, null, bodyStr);
    }

    /**
     * 构建上下文
     * @param notifyType
     * @param formParam
     * @param bodyStr
     * @return
     */
    public static SupayNotifyContext buildNotifyContext(ChannelNotifyType notifyType, Map formParam, String bodyStr) {
        SupayNotifyContext notifyCtx = new SupayNotifyContext();
        notifyCtx.setNotifyType(notifyType);
        notifyCtx.setFormParam(formParam);
        notifyCtx.setBodyStr(bodyStr);
        return notifyCtx;
    }

    /**
     * 通知处理失败
     * @param retStr
     * @return
     */
    public SupayNotifyContext result(String retStr) {
        this.retStr = retStr;
        return this;
    }

}