/*******************************************************************************
 * @(#)SupayScanPayResponse.java 2020年05月29日 12:26
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.enums.SupayPayStatus;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.enums.SupayPayUserType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <b>Application name：</b> SupayScanPayResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class SupayMicroPayResponse extends SupayPayResponse {
    /** 商户订单号 */
    private String outTradeNo;
    /** 支付渠道交易号（支付成功时有值） */
    private String serviceTradeNo;

    /** 支付用户id */
    protected String payUserId;
    /** 支付用户类型 */
    protected SupayPayUserType payUserType;
    /** 附加数据 */
    protected String attach;
    /** 支付时间 */
    protected Date payTime;

}