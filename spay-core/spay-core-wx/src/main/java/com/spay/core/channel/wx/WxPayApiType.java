/*******************************************************************************
 * @(#)WxTradeType.java 2017年04月18日 17:33 
 * Copyright 2017 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx;

import com.spay.core.channel.ChannelApiType;

/**
 * <b>Application name：</b> WxPayApiType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class WxPayApiType extends ChannelApiType {
    /** 中国国内 */
    public static WxPayApiType BASE_URL_CHINA1 = new WxPayApiType("", "https://api.mch.weixin.qq.com");
    /** 中国国内(备用域名) */
    public static WxPayApiType BASE_URL_CHINA2 = new WxPayApiType("", "https://api2.mch.weixin.qq.com");
    /** 东南亚 */
    public static WxPayApiType BASE_URL_HK = new WxPayApiType("", "https://apihk.mch.weixin.qq.com");
    /** 其它 */
    public static WxPayApiType BASE_URL_US = new WxPayApiType("", "https://apius.mch.weixin.qq.com");
    /** 沙箱环境 */
    public static WxPayApiType SAND_BOX_URL = new WxPayApiType("", "/sandboxnew");

    /** 统一下单 */
    public static WxPayApiType UNIFIED_ORDER = new WxPayApiType("", "/pay/unifiedorder");

    /** 红包接口 */
    public static WxPayApiType REDPACKET = new WxPayApiType("", "/mmpaymkttransfers/sendredpack");

    /** 查询订单 */
    public static WxPayApiType PAY_QUERY = new WxPayApiType("", "/pay/orderquery");
    /** 关闭订单 */
    public static WxPayApiType CLOSE = new WxPayApiType("", "/pay/closeorder");
    /** 申请退款 */
    public static WxPayApiType REFUND = new WxPayApiType("", "/secapi/pay/refund");
    /** 查询退款 */
    public static WxPayApiType REFUND_QUERY = new WxPayApiType("", "/pay/refundquery");
    /** 下载对账单 */
    public static WxPayApiType DOWNLOAD_BILL = new WxPayApiType("", "/pay/downloadbill");
    /** 获取仿真系统key */
    public static WxPayApiType GET_SIGN_KEY = new WxPayApiType("", "/pay/getsignkey");

    public WxPayApiType(String name, String url) {
        super(name, url);
    }
}