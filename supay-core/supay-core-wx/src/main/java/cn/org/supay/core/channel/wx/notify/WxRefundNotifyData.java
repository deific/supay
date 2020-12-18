/*******************************************************************************
 * @(#)WxPayNotifyData.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.notify;

import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.notify.ChannelNotifyData;
import cn.org.supay.core.channel.wx.data.WxPayBaseResponse;
import lombok.Data;

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
public class WxRefundNotifyData extends WxPayBaseResponse implements ChannelNotifyData {
    /**
     * 微信订单号
     */
    @XmlField("transaction_id")
    private String transactionId;

    /**
     * 商户系统内部的订单号
     */
    @XmlField("out_trade_no")
    private String outTradeNo;

    /**
     * 微信退款单号
     */
    @XmlField("refund_id")
    private String refundId;

    /**
     * 商户退款单号
     */
    @XmlField("out_refund_no")
    private String outRefundNo;

    /**
     * 订单总金额，单位为分，只能为整数，详见支付金额
     */
    @XmlField("total_fee")
    private Integer totalFee;

    /**
     * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
     */
    @XmlField("settlement_total_fee")
    private Integer settlementTotalFee;

    /**
     * 申请退款金额
     */
    @XmlField("refund_fee")
    private Integer refundFee;

    /**
     * 退款金额
     */
    @XmlField("settlement_refund_fee")
    private Integer settlementRefundFee;

    /**
     * 退款状态
     */
    @XmlField("refund_status")
    private String refundStatus;

    /**
     * 退款成功时间
     */
    @XmlField("success_time")
    private String successTime;

    /**
     * 退款入账账户
     */
    @XmlField("refund_recv_accout")
    private String refundRecvAccout;

    /**
     * 退款资金来源
     */
    @XmlField("refund_account")
    private String refund_account;

    /**
     * 退款发起来源
     */
    @XmlField("refund_request_source")
    private String refundRequestSource;
}