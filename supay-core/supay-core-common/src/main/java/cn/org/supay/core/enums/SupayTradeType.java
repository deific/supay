/*******************************************************************************
 * @(#)SupayTradeType.java 2020年05月15日 22:47
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.enums;

/**
 * <b>Application name：</b> SupayTradeType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:47 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public enum SupayTradeType {
    TRADE_DIRECT_PAY("30", "直接交易"),
    TRADE_SECURED_PAY("34", "担保交易"),
    TRADE_CONFIRM_PAY("35", "确认支付"),
    TRADE_REFUND("31", "退款"),
    TRADE_CAS("32", "清分"),
    TRADE_WITHDRAW("33", "提现"),
    TRADE_PAY_QUERY("36", "支付查询"),
    TRADE_REFUND_QUERY("37", "退款查询"),
    TRADE_DAC("38", "代付交易"),
    TRADE_RED_PACKET("39", "发送红包"),
    TRADE_QUERY("40", "交易查询"),
    TRADE_RECHARGE("41", "充值"),
    TRADE_SHARING("42", "分账"),
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
    SupayTradeType(String code, String name) {
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
    public static SupayTradeType valueOfByCode(String code) {
        SupayTradeType[] tradeTypes = SupayTradeType.values();
        for (SupayTradeType tradeType : tradeTypes) {
            if (tradeType.getCode().equals(code)) {
                return tradeType;
            }
        }
        return null;
    }
}