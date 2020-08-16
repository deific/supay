/*******************************************************************************
 * @(#)SupayRefundRequest.java 2020年05月29日 12:26
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.channel.data.Request;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

/**
 * <b>Application name：</b> SupayRefundRequest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SupayRefundRequest extends SupayBaseRequest implements Request {
    /** 原始支付单号(业务侧) */
    protected String originTradeNo;
    /**  业务退款单号(业务侧) */
    protected String refundTradeNo;
    /** 原始支付金额 */
    protected BigDecimal totalAmount;
    /**  退款金额 */
    protected BigDecimal refundAmount;
    /** 退款成功通知地址 */
    protected String notifyUrl;
}