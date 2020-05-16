/*******************************************************************************
 * @(#)SpayChannelType.java 2020年05月16日 15:20
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.enums;

/**
 * <b>Application name：</b> SpayChannelType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 15:20 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public enum SpayChannelType {
    WECHAT("1", "微信支付"),
    ALIPAY("2", "支付宝"),
    UNION_PAY("3", "银联"),
    QQ_PAY("4", "QQ支付"),
    JD_PAY("5", "京东支付"),
            ;

    /** 编码 */
    private String code;
    /** 名称 */
    private String name;

    /**
     * 用户终端
     * @param code
     * @param name
     */
    SpayChannelType(String code, String name) {
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
    public static SpayChannelType valueOfByCode(String code) {
        SpayChannelType[] tradeTypes = SpayChannelType.values();
        for (SpayChannelType tradeType : tradeTypes) {
            if (tradeType.getCode().equals(code)) {
                return tradeType;
            }
        }
        return null;
    }
}