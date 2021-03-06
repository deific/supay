/*******************************************************************************
 * @(#)SupayRefundQueryResponse.java 2020年05月29日 12:26
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.enums.SupayRefundStatus;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <b>Application name：</b> SupayRefundQueryResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class SupayRefundQueryResponse extends SupayBaseResponse implements Response {

    /** 退款单号 */
    public String refundTradeNo;

    /** 原支付单号 */
    public String originTradeNo;

    /** 退款金额 */
    private BigDecimal refundAmount;

    /** 退款状态 */
    public SupayRefundStatus refundStatus;

    /** 退款时间 */
    private Date refundTime;
}