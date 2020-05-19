/*******************************************************************************
 * @(#)WxPayOrderQueryRequest.java 2020年05月19日 23:42
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.data;

import com.spay.core.annotation.XmlField;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <b>Application name：</b> WxPayOrderQueryRequest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月19日 23:42 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class WxPayOrderQueryRequest extends WxPayBaseRequest {
    /** 微信的订单号，优先使用,与outTradeNo二选一 */
    @XmlField("transaction_id")
    private String transactionId;
    /** 商户系统内部的订单号，当没提供transaction_id时需要传这个。 */
    @XmlField("out_trade_no")
    private String outTradeNo;
}