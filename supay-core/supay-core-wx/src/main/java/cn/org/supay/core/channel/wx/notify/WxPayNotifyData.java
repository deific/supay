/*******************************************************************************
 * @(#)WxPayNotifyData.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.notify;

import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.notify.ChannelNotifyData;
import cn.org.supay.core.channel.wx.data.WxPayBaseResponse;
import cn.org.supay.core.channel.wx.data.WxPayCoupon;
import lombok.Data;

import java.util.List;

/**
 * <b>Application name：</b> WxPayNotifyData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年06月06日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class WxPayNotifyData extends WxPayBaseResponse implements ChannelNotifyData {
    /**
     * <pre>
     * 设备号
     * device_info
     * 否
     * String(32)
     * 013467007045764
     * 微信支付分配的终端设备号，
     * </pre>
     */
    @XmlField("device_info")
    private String deviceInfo;

    /**
     * <pre>
     * 用户标识
     * openid
     * 是
     * String(128)
     * wxd930ea5d5a258f4f
     * 用户在商户appid下的唯一标识
     * </pre>
     */
    @XmlField("openid")
    private String openid;

    /**
     * <pre>
     * 是否关注公众账号
     * is_subscribe
     * 否
     * String(1)
     * Y
     * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     * </pre>
     */
    @XmlField("is_subscribe")
    private String isSubscribe;


    /**
     * <pre>
     * 交易类型
     * trade_type
     * 是
     * String(16)
     * JSAPI	JSAPI、NATIVE、APP
     * </pre>
     */
    @XmlField("trade_type")
    private String tradeType;


    /**
     * <pre>
     * 付款银行
     * bank_type
     * 是
     * String(16)
     * CMC
     * 银行类型，采用字符串类型的银行标识，银行类型见银行列表
     * </pre>
     */
    @XmlField("bank_type")
    private String bankType;


    /**
     * <pre>
     * 订单金额
     * total_fee
     * 是
     * Int
     * 100
     * 订单总金额，单位为分
     * </pre>
     */
    @XmlField("total_fee")
    private Integer totalFee;
    /**
     * <pre>
     * 应结订单金额
     * settlement_total_fee
     * 否
     * Int
     * 100
     * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
     * </pre>
     */
    @XmlField("settlement_total_fee")
    private Integer settlementTotalFee;
    /**
     * <pre>
     * 货币种类
     * fee_type
     * 否
     * String(8)
     * CNY
     * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     */
    @XmlField("fee_type")
    private String feeType;
    /**
     * <pre>
     * 现金支付金额
     * cash_fee
     * 是
     * Int
     * 100
     * 现金支付金额订单现金支付金额，详见支付金额
     * </pre>
     */
    @XmlField("cash_fee")
    private Integer cashFee;
    /**
     * <pre>
     * 现金支付货币类型
     * cash_fee_type
     * 否
     * String(16)
     * CNY
     * 货币类型，符合ISO4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
     * </pre>
     */
    @XmlField("cash_fee_type")
    private String cashFeeType;
    /**
     * <pre>
     * 总代金券金额
     * coupon_fee
     * 否
     * Int
     * 10
     * 代金券金额<=订单金额，订单金额-代金券金额=现金支付金额，详见支付金额
     * </pre>
     */
    @XmlField("coupon_fee")
    private Integer couponFee;

    /**
     * <pre>
     * 代金券使用数量
     * coupon_count
     * 否
     * Int
     * 1
     * 代金券使用数量
     * </pre>
     */
    @XmlField("coupon_count")
    private Integer couponCount;

    /**
     * 使用优惠券列表
     */
    private List<WxPayCoupon> couponList;

    /**
     * <pre>
     * 微信支付订单号
     * transaction_id
     * 是
     * String(32)
     * 1217752501201407033233368018
     * 微信支付订单号
     * </pre>
     */
    @XmlField("transaction_id")
    private String transactionId;

    /**
     * <pre>
     * 商户订单号
     * out_trade_no
     * 是
     * String(32)
     * 1212321211201407033568112322
     * 商户系统的订单号，与请求一致。
     * </pre>
     */
    @XmlField("out_trade_no")
    private String outTradeNo;

    /**
     * <pre>
     * 商家数据包
     * attach
     * 否
     * String(128)
     * 123456
     * 商家数据包，原样返回
     * </pre>
     */
    @XmlField("attach")
    private String attach;

    /**
     * <pre>
     * 支付完成时间
     * time_end
     * 是
     * String(14)
     * 20141030133525
     * 支付完成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则
     * </pre>
     */
    @XmlField("time_end")
    private String timeEnd;

}