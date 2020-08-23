/*******************************************************************************
 * @(#)WxPayOrderQueryResponse.java 2020年05月19日 23:44
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.aggregate.data.*;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayPayStatus;
import cn.org.supay.core.enums.SupayRefundStatus;

/**
 * <b>Application name：</b> WxPayOrderQueryResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月19日 23:44 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class WxPayOrderQueryResponse extends WxPayBaseResponse implements AggregateResponseConvert {
    /** 微信支付分配的终端设备号 */
    @XmlField("device_info")
    private String deviceInfo;
    /** 用户在商户appid下的唯一标识 */
    @XmlField("openid")
    private String openid;
    /** 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效 */
    @XmlField("is_subscribe")
    private String isSubscribe;
    /** 调用接口提交的交易类型，取值如下：JSAPI，NATIVE，APP，MICROPAY，详细说明见参数规定*/
    @XmlField("trade_type")
    private String tradeType;
    /** SUCCESS—支付成功,ORDER_REFUND—转入退款,NOTPAY—未支付,CLOSED—已关闭,REVOKED—已撤销（刷卡支付）,USERPAYING--用户支付中,PAYERROR--支付失败(其他原因，如银行返回失败) */
    @XmlField("trade_state")
    private String tradeState;
    /** 银行类型，采用字符串类型的银行标识 */
    @XmlField("bank_type")
    private String bankType;
    /** 订单总金额，单位为分 */
    @XmlField("total_fee")
    private Integer totalFee;
    /** 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额 */
    @XmlField("settlement_total_fee")
    private Integer settlementTotalFee;
    /** 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型 */
    @XmlField("fee_type")
    private String feeType;
    /** 现金支付金额订单现金支付金额，详见支付金额 */
    @XmlField("cash_fee")
    private Integer cashFee;
    /** 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型 */
    @XmlField("cash_fee_type")
    private String cashFeeType;
    /** “代金券”金额<=订单金额，订单金额-“代金券”金额=现金支付金额，详见支付金额 */
    @XmlField("coupon_fee")
    private Integer couponFee;
    /** 代金券使用数量 */
    @XmlField("coupon_count")
    private Integer couponCount;
    /** 微信支付订单号 */
    @XmlField("transaction_id")
    private String transactionId;
    /** 商户系统的订单号，与请求一致。 */
    @XmlField("out_trade_no")
    private String outTradeNo;
    /** 附加数据 */
    @XmlField("attach")
    private String attach;
    /** 订单支付时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010。其他详见时间规则 */
    @XmlField("time_end")
    private String timeEnd;
    /** 对当前查询订单状态的描述和下一步操作的指引*/
    @XmlField("trade_state_desc")
    private String tradeStateDesc;

    @Override
    public SupayBaseResponse convertResponse(SupayContext context) {
        SupayPayQueryResponse payQueryResponse = SupayPayQueryResponse.builder()
                .originTradeNo(this.outTradeNo)
                .payTime(DateUtil.parse(this.timeEnd, "yyyyMMddHHmmss"))
                .serviceTradeNo(this.transactionId)
                .build();
        payQueryResponse.setResultCode(this.getResultCode());
        payQueryResponse.setResultMsg(this.getReturnMsg());
        payQueryResponse.setSuccess(this.checkResult());
        return payQueryResponse;
    }

    /**
     * 转换退款状态
     * SUCCESS—支付成功,ORDER_REFUND—转入退款,NOTPAY—未支付,CLOSED—已关闭,REVOKED—已撤销（刷卡支付）,USERPAYING--用户支付中,PAYERROR--支付失败(其他原因，如银行返回失败)
     * @return
     */
    private SupayPayStatus convertPayStatus() {
        switch (this.tradeState) {
            case "SUCCESS":
                return SupayPayStatus.PAY_SUCCESS;
            case "NOTPAY":
                return SupayPayStatus.NO_PAY;
            case "ORDER_REFUND":
            case "CLOSED":
            case "REVOKED":
            case "PAYERROR":
                return SupayPayStatus.PAY_FAIL;
            case "USERPAYING":
                return SupayPayStatus.PAY_PROCESSING;
        }
        return null;
    }
}