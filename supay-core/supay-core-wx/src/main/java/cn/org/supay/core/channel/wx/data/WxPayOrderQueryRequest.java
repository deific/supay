/*******************************************************************************
 * @(#)WxPayOrderQueryRequest.java 2020年05月19日 23:42
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.aggregate.data.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <b>Application name：</b> WxPayOrderQueryRequest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月19日 23:42 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class WxPayOrderQueryRequest extends WxPayBaseRequest implements AggregateRequestConvert {
    /** 微信的订单号，优先使用,与outTradeNo二选一 */
    @XmlField("transaction_id")
    private String transactionId;
    /** 商户系统内部的订单号，当没提供transaction_id时需要传这个。 */
    @XmlField("out_trade_no")
    private String outTradeNo;

    @Override
    public WxPayOrderQueryRequest convertRequest(SupayBaseRequest request) {
        SupayPayQueryRequest payQueryRequest = (SupayPayQueryRequest) request;
        this.setOutTradeNo(payQueryRequest.getOutTradeNo());
        return this;
    }
}