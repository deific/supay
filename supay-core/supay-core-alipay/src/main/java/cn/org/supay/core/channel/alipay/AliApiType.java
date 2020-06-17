/*******************************************************************************
 * @(#)AliPayApiType.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay;

import cn.org.supay.core.channel.ChannelApiType;

/**
 * <b>Application name：</b> AliApiType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月25日 19:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class AliApiType extends ChannelApiType {
    /** 网页支付 */
    public static AliApiType PAGE = new AliApiType("网页支付", "alipay.trade.page.pay");
    /** APP支付 */
    public static AliApiType APP = new AliApiType("APP支付","alipay.trade.app.pay");
    /** 手机网站支付 */
    public static AliApiType WAP = new AliApiType("手机网站支付", "alipay.trade.wap.pay");
    /** 扫码付 */
    public static AliApiType SWEEPPAY = new AliApiType("扫码付", "alipay.trade.precreate");
    /** 条码付 */
    public static AliApiType BAR_CODE = new AliApiType("条码付", "alipay.trade.pay");
    /** 声波付 */
    public static AliApiType WAVE_CODE = new AliApiType("声波付", "alipay.trade.pay");
    /** 小程序 */
    public static AliApiType MINAPP = new AliApiType("小程序", "alipay.trade.create");
    /** 刷脸付 */
    public static AliApiType SECURITY_CODE = new AliApiType("刷脸付", "alipay.trade.pay");

    /** 统一收单交易结算接口 */
    public static AliApiType SETTLE = new AliApiType("统一收单交易结算接口", "alipay.trade.order.settle");
    /** 交易订单查询 */
    public static AliApiType QUERY = new AliApiType("交易订单查询", "alipay.trade.query");
    /** 交易订单关闭 */
    public static AliApiType CLOSE = new AliApiType("交易订单关闭", "alipay.trade.close");
    /** 交易订单撤销 */
    public static AliApiType CANCEL = new AliApiType("交易订单撤销", "alipay.trade.cancel");
    /** 退款 */
    public static AliApiType REFUND = new AliApiType("退款", "alipay.trade.refund");
    /** 退款查询 */
    public static AliApiType REFUNDQUERY = new AliApiType("退款查询", "alipay.trade.fastpay.refund.query");

    public AliApiType(String name, String url) {
        super(name, url);
    }
}