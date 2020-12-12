/*******************************************************************************
 * @(#)SupayPayType.java 2020年05月16日 08:46
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.enums;

/**
 * <b>Application name：</b> SupayPayUserType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 08:46 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public enum SupayPayUserType {
    WX_OPEN_ID("wechat", SupayChannelType.WECHAT,"微信公众号用户"),
    ALI_PAY_USER("alipay", SupayChannelType.ALIPAY,"支付宝用户"),
    ;

    /** 编码 */
    private String code;
    /** 渠道 */
    private SupayChannelType channel;
    /** 名称 */
    private String name;

    /**
     * 用户终端
     * @param code
     * @param name
     */
    SupayPayUserType(String code, SupayChannelType channel, String name) {
        this.code = code;
        this.channel = channel;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public SupayChannelType getChannel() {
        return channel;
    }

    /**
     * 根据编码获取支付渠道
     * @param code
     * @return
     */
    public static SupayPayUserType valueOfByCode(String code) {
        SupayPayUserType[] tradeTypes = SupayPayUserType.values();
        for (SupayPayUserType tradeType : tradeTypes) {
            if (tradeType.getCode().equals(code)) {
                return tradeType;
            }
        }
        return null;
    }
}