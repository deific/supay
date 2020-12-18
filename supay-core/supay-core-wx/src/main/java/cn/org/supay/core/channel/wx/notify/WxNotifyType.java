/*******************************************************************************
 * @(#)WxPayNotifyData.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.notify;

import cn.org.supay.core.channel.notify.ChannelNotifyType;
import cn.org.supay.core.enums.SupayChannelType;

/**
 * <b>Application name：</b> WxNotifyType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年06月06日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public enum WxNotifyType implements ChannelNotifyType {
    PAY_NOTIFY("PAY_NOTIFY", "支付结果通知"),
    REFUND_NOTIFY("REFUND_NOTIFY", "退款结果通知");

    /** 编码 */
    private String code;
    /** 名称 */
    private String name;

    /**
     * 用户终端
     * @param code
     * @param name
     */
    WxNotifyType(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据编码获取支付渠道
     * @param code
     * @return
     */
    public static WxNotifyType valueOfByCode(String code) {
        WxNotifyType[] tradeTypes = WxNotifyType.values();
        for (WxNotifyType tradeType : tradeTypes) {
            if (tradeType.getCode().equals(code)) {
                return tradeType;
            }
        }
        return null;
    }

    @Override
    public SupayChannelType channelType() {
        return SupayChannelType.WECHAT;
    }
}