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
public enum SupayPayStatus {
    NOT_EXIST("-1", "订单不存在"),
    NO_PAY("0", "未支付"),
    PAY_SUCCESS("1", "支付成功"),
    PAY_FAIL("2", "支付失败"),
    PAY_PROCESSING("3", "支付中"),
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
    SupayPayStatus(String code, String name) {
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
    public static SupayPayStatus valueOfByCode(String code) {
        SupayPayStatus[] tradeTypes = SupayPayStatus.values();
        for (SupayPayStatus tradeType : tradeTypes) {
            if (tradeType.getCode().equals(code)) {
                return tradeType;
            }
        }
        return null;
    }
}