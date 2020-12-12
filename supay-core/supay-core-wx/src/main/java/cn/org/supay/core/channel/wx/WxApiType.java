/*******************************************************************************
 * @(#)WxPayApiType.java 2020年05月16日 09:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx;

import cn.org.supay.core.channel.ChannelApiType;

/**
 * <b>Application name：</b> WxApiType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class WxApiType extends ChannelApiType {
    /** 中国国内 */
    public static WxApiType BASE_URL_CHINA1 = new WxApiType("", "https://api.mch.weixin.qq.com");
    /** 中国国内(备用域名) */
    public static WxApiType BASE_URL_CHINA2 = new WxApiType("", "https://api2.mch.weixin.qq.com");
    /** 东南亚 */
    public static WxApiType BASE_URL_HK = new WxApiType("", "https://apihk.mch.weixin.qq.com");
    /** 其它 */
    public static WxApiType BASE_URL_US = new WxApiType("", "https://apius.mch.weixin.qq.com");
    /** 沙箱环境 */
    public static WxApiType SAND_BOX_URL = new WxApiType("", "/sandboxnew");

    /** 统一下单 */
    public static WxApiType UNIFIED_ORDER = new WxApiType("", "/pay/unifiedorder");

    /** 刷卡支付统一下单 */
    public static WxApiType MICRO_ORDER = new WxApiType("", "/pay/micropay");
    /** 撤销订单 */
    public static WxApiType REVERSE = new WxApiType("", "/pay/reverse");

    /** 红包接口 */
    public static WxApiType REDPACKET = new WxApiType("", "/mmpaymkttransfers/sendredpack");

    /** 查询订单 */
    public static WxApiType PAY_QUERY = new WxApiType("", "/pay/orderquery");
    /** 关闭订单 */
    public static WxApiType CLOSE = new WxApiType("", "/pay/closeorder");
    /** 申请退款 */
    public static WxApiType REFUND = new WxApiType("", "/secapi/pay/refund", true);
    /** 查询退款 */
    public static WxApiType REFUND_QUERY = new WxApiType("", "/pay/refundquery");
    /** 下载对账单 */
    public static WxApiType DOWNLOAD_BILL = new WxApiType("", "/pay/downloadbill");
    /** 获取仿真系统key */
    public static WxApiType GET_SIGN_KEY = new WxApiType("", "/pay/getsignkey");

    public WxApiType(String name, String url) {
        super(name, url);
    }
    public WxApiType(String name, String url, boolean sslCertRequired) {
        super(name, url, sslCertRequired);
    }
}