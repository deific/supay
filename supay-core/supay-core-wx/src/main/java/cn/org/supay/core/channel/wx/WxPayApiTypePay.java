/*******************************************************************************
 * @(#)WxTradeType.java 2017年04月18日 17:33 
 * Copyright 2017 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx;

import cn.org.supay.core.channel.PayChannelApiType;

/**
 * <b>Application name：</b> WxPayApiTypePay.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class WxPayApiTypePay extends PayChannelApiType {
    /** 中国国内 */
    public static WxPayApiTypePay BASE_URL_CHINA1 = new WxPayApiTypePay("", "https://api.mch.weixin.qq.com");
    /** 中国国内(备用域名) */
    public static WxPayApiTypePay BASE_URL_CHINA2 = new WxPayApiTypePay("", "https://api2.mch.weixin.qq.com");
    /** 东南亚 */
    public static WxPayApiTypePay BASE_URL_HK = new WxPayApiTypePay("", "https://apihk.mch.weixin.qq.com");
    /** 其它 */
    public static WxPayApiTypePay BASE_URL_US = new WxPayApiTypePay("", "https://apius.mch.weixin.qq.com");
    /** 沙箱环境 */
    public static WxPayApiTypePay SAND_BOX_URL = new WxPayApiTypePay("", "/sandboxnew");

    /** 统一下单 */
    public static WxPayApiTypePay UNIFIED_ORDER = new WxPayApiTypePay("", "/pay/unifiedorder");

    /** 红包接口 */
    public static WxPayApiTypePay REDPACKET = new WxPayApiTypePay("", "/mmpaymkttransfers/sendredpack");

    /** 查询订单 */
    public static WxPayApiTypePay PAY_QUERY = new WxPayApiTypePay("", "/pay/orderquery");
    /** 关闭订单 */
    public static WxPayApiTypePay CLOSE = new WxPayApiTypePay("", "/pay/closeorder");
    /** 申请退款 */
    public static WxPayApiTypePay REFUND = new WxPayApiTypePay("", "/secapi/pay/refund");
    /** 查询退款 */
    public static WxPayApiTypePay REFUND_QUERY = new WxPayApiTypePay("", "/pay/refundquery");
    /** 下载对账单 */
    public static WxPayApiTypePay DOWNLOAD_BILL = new WxPayApiTypePay("", "/pay/downloadbill");
    /** 获取仿真系统key */
    public static WxPayApiTypePay GET_SIGN_KEY = new WxPayApiTypePay("", "/pay/getsignkey");

    public WxPayApiTypePay(String name, String url) {
        super(name, url);
    }
}