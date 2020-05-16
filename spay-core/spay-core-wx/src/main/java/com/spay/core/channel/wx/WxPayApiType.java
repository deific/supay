/*******************************************************************************
 * @(#)WxTradeType.java 2017年04月18日 17:33 
 * Copyright 2017 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx;

/**
 * <b>Application name：</b> WxPayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public enum WxPayApiType {
    /** 中国国内 */
    BASE_URL_CHINA1("https://api.mch.weixin.qq.com"),
    /** 中国国内(备用域名) */
    BASE_URL_CHINA2("https://api2.mch.weixin.qq.com"),
    /** 东南亚 */
    BASE_URL_HK("https://apihk.mch.weixin.qq.com"),
    /** 其它 */
    BASE_URL_US("https://apius.mch.weixin.qq.com"),
    /** 沙箱环境 */
    SAND_BOX_URL("/sandboxnew"),

    /** 统一下单 */
    UNIFIED_ORDER("/pay/unifiedorder"),

    //红包接口
    REDPACKET("/mmpaymkttransfers/sendredpack"),

    //查询订单
    QUERY("/pay/orderquery"),
    //关闭订单
    CLOSE("/pay/closeorder"),
    //申请退款
    REFUND("/secapi/pay/refund"),
    //查询退款
    REFUNDQUERY("/pay/refundquery"),
    //下载对账单
    DOWNLOADBILL("/pay/downloadbill"),
    //不知道交易类型，主要用于回调通知，回调后不清楚交易类型，以此定义
    UNAWARE("UNAWARE"),
    //获取仿真系统key
    GETSIGNKEY("/pay/getsignkey"),

    ;
    WxPayApiType(String method) {
        this.method = method;
    }

    private String method;

    public String getType() {
        return this.name();
    }
    public String getMethod() {
        return this.method;
    }
}