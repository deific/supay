/*******************************************************************************
 * @(#)AliPayApiType.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay;

import cn.org.supay.core.channel.PayChannelApiType;

/**
 * <b>Application name：</b> AliPayApiType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月25日 19:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class AliPayApiType extends PayChannelApiType {
    /** 网页支付 */
    public static AliPayApiType PAGE = new AliPayApiType("网页支付", "alipay.trade.page.pay");
    /** APP支付 */
    public static AliPayApiType APP = new AliPayApiType("APP支付","alipay.trade.app.pay");
    /** 手机网站支付 */
    public static AliPayApiType WAP = new AliPayApiType("手机网站支付", "alipay.trade.wap.pay");
    /** 扫码付 */
    public static AliPayApiType SWEEPPAY = new AliPayApiType("扫码付", "alipay.trade.precreate");
    /** 条码付 */
    public static AliPayApiType BAR_CODE = new AliPayApiType("条码付", "alipay.trade.pay");
    /** 声波付 */
    public static AliPayApiType WAVE_CODE = new AliPayApiType("声波付", "alipay.trade.pay");
    /** 小程序 */
    public static AliPayApiType MINAPP = new AliPayApiType("小程序", "alipay.trade.create");
    /** 刷脸付 */
    public static AliPayApiType SECURITY_CODE = new AliPayApiType("刷脸付", "alipay.trade.pay");

    /** 统一收单交易结算接口 */
    public static AliPayApiType SETTLE = new AliPayApiType("统一收单交易结算接口", "alipay.trade.order.settle");
    /** 交易订单查询 */
    public static AliPayApiType QUERY = new AliPayApiType("交易订单查询", "alipay.trade.query");
    /** 交易订单关闭 */
    public static AliPayApiType CLOSE = new AliPayApiType("交易订单关闭", "alipay.trade.close");
    /** 交易订单撤销 */
    public static AliPayApiType CANCEL = new AliPayApiType("交易订单撤销", "alipay.trade.cancel");
    /** 退款 */
    public static AliPayApiType REFUND = new AliPayApiType("退款", "alipay.trade.refund");
    /** 退款查询 */
    public static AliPayApiType REFUNDQUERY = new AliPayApiType("退款查询", "alipay.trade.fastpay.refund.query");

    public AliPayApiType(String name, String url) {
        super(name, url);
    }
}