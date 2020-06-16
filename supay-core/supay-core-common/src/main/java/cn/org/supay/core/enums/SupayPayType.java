/*******************************************************************************
 * @(#)SupayPayType.java 2020年05月16日 08:46
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.enums;

/**
 * <b>Application name：</b> SupayPayType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 08:46 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public enum SupayPayType {
    WX_SCAN_PAY("NATIVE", SupayChannelType.WECHAT,"扫码支付"),
    WX_MP_PAY("JSAPI", SupayChannelType.WECHAT,"公众号支付"),
    WX_APP_PAY("APP", SupayChannelType.WECHAT,"APP支付"),
    WX_MICRO_PAY("MICROPAY", SupayChannelType.WECHAT,"刷卡付"),
    WX_H5_PAY("MWEB", SupayChannelType.WECHAT,"H5支付"),
    WX_FACE_PAY("FACEPAY", SupayChannelType.WECHAT,"刷脸付"),

    ALI_PAGE_PAY("PAGE", SupayChannelType.ALIPAY,"PC端网页支付"),
    ALI_WAP_PAY("WAP", SupayChannelType.ALIPAY,"手机端网页支付"),
    ALI_APP_PAY("APP", SupayChannelType.ALIPAY,"APP支付"),
    ALI_FACE_PAY("FACEPAY", SupayChannelType.ALIPAY,"刷脸付"),
    ALI_COMMON_PAY("COMMON", SupayChannelType.ALIPAY,"统一收单交易支付接口"),
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
    SupayPayType(String code, SupayChannelType channel, String name) {
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
    public static SupayPayType valueOfByCode(String code) {
        SupayPayType[] tradeTypes = SupayPayType.values();
        for (SupayPayType tradeType : tradeTypes) {
            if (tradeType.getCode().equals(code)) {
                return tradeType;
            }
        }
        return null;
    }
}