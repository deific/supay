/*******************************************************************************
 * @(#)SupayPayType.java 2020年05月16日 08:46
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.enums;

/**
 * <b>Application name：</b> SupayPayStatus.java <br>
 * <b>Application describing： </b> 支付状态
 * <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 08:46 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public enum SupayRefundStatus {
    NO_REFUND("0", "未退款"),
    REFUND_SUCCESS("1", "退款成功"),
    REFUND_FAIL("2", "退款失败"),
    REFUND_PROCESSING("3", "退款中"),
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
    SupayRefundStatus(String code, String name) {
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
    public static SupayRefundStatus valueOfByCode(String code) {
        SupayRefundStatus[] tradeTypes = SupayRefundStatus.values();
        for (SupayRefundStatus tradeType : tradeTypes) {
            if (tradeType.getCode().equals(code)) {
                return tradeType;
            }
        }
        return null;
    }
}