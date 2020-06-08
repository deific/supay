/*******************************************************************************
 * @(#)AliPayNotifyData.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.notify;

import cn.org.supay.core.channel.notify.NotifyData;
import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * <b>Application name：</b> AliPayNotifyData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年06月06日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class AliPayNotifyData implements NotifyData {
    /**
     * 通知时间
      */
    @JSONField(name = "notify_time")
    private Date notifyTime;
    /**
     * 通知类型
     */
    @JSONField(name = "notify_type")
    private String notifyType;
    /**
     * 通知校验ID
     */
    @JSONField(name = "notify_id")
    private String notifyId;
    /**
     * 支付宝分配给开发者的应用Id
     */
    @JSONField(name = "app_id")
    private String appId;
    /**
     * 编码格式
     */
    private String charset;
    /**
     * 接口版本
     */
    private String version;
    /**
     * 签名类型
     */
    @JSONField(name = "sign_type")
    private String signType;
    /**
     * 签名
     */
    private String sign;
    /**
     * 支付宝交易号
     */
    @JSONField(name = "trade_no")
    private String tradeNo;
    /**
     * 商户订单号
     */
    @JSONField(name = "out_trade_no")
    private String outTradeNo;
    /**
     * 商户业务号
     */
    @JSONField(name = "out_biz_no")
    private String outBizNo;
    /**
     * 买家支付宝用户号
     */
    @JSONField(name = "buyer_id")
    private String buyerId;
    /**
     * 买家支付宝账号
     */
    @JSONField(name = "buyer_logon_id")
    private String buyerLogonId;
    /**
     * 卖家支付宝用户号
     */
    @JSONField(name = "seller_id")
    private String sellerId;
    /**
     * 卖家支付宝账号
     */
    @JSONField(name = "seller_email")
    private String sellerEmail;
    /**
     * 交易状态
     */
    @JSONField(name = "trade_status")
    private String tradeStatus;
    /**
     * 订单金额
     */
    @JSONField(name = "total_amount")
    private BigDecimal totalAmount;
    /**
     * 实收金额
     */
    @JSONField(name = "receipt_amount")
    private BigDecimal receiptAmount;
    /**
     * 开票金额
     */
    @JSONField(name = "invoice_amount")
    private BigDecimal invoiceAmount;
    /**
     * 付款金额
     */
    @JSONField(name = "buyer_pay_amount")
    private BigDecimal buyerPayAmount;
    /**
     * 集分宝金额
     */
    @JSONField(name = "point_amount")
    private BigDecimal pointAmount;
    /**
     * 总退款金额
     */
    @JSONField(name = "refund_fee")
    private BigDecimal refundFee;
    /**
     * 订单标题
     */
    @JSONField(name = "subject")
    private String subject;
    /**
     * 商品描述
     */
    @JSONField(name = "body")
    private String body;
    /**
     * 交易创建时间
     */
    @JSONField(name = "gmt_create")
    private Date gmtCreate;
    /**
     * 交易付款时间
     */
    @JSONField(name = "gmt_payment")
    private Date gmtPayment;
    /**
     * 交易退款时间 	Date 	否 	该笔交易的退款时间。格式为yyyy-MM-dd HH:mm:ss.S 	2015-04-28 15:45:57.320
     */
    @JSONField(name = "gmt_refund")
    private Date gmtRefund;
    /**
     * 交易结束时间 	Date 	否 	该笔交易结束时间。格式为yyyy-MM-dd HH:mm:ss 	2015-04-29 15:45:57
     */
    @JSONField(name = "gmt_close")
    private Date gmtClose;
    /**
     * 支付金额信息 	String(512) 	否 	支付成功的各个渠道金额信息，详见下表 资金明细信息说明 	[{“amount”:“15.00”,“fundChannel”:“ALIPAYACCOUNT”}]
     */
    @JSONField(name = "fund_bill_list")
    private String fundBillList;
    /**
     * 回传参数 	String(512) 	否 	公共回传参数，如果请求时传递了该参数，则返回给商户时会在异步通知时将该参数原样返回。本参数必须进行UrlEncode之后才可以发送给支付宝 	merchantBizType%3d3C%26merchantBizNo%3d2016010101111
     */
    @JSONField(name = "passback_params")
    private String passbackParams;
    /**
     * 优惠券信息 	String 	否 	本交易支付时所使用的所有优惠券信息，详见下表 优惠券信息说明 	[{“amount”:“0.20”,“merchantContribute”:“0.00”,“name”:“一键创建券模板的券名称”,“otherContribute”:“0.20”,“type”:“ALIPAY_DISCOUNT_VOUCHER”,“memo”:“学生卡8折优惠”]
     */
    @JSONField(name = "voucher_detail_list")
    private String voucherDetailList;

}