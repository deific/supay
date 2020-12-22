/*******************************************************************************
 * @(#)SupayPayQueryResponse.java 2020年05月29日 12:26
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayStatus;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.enums.SupayPayUserType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * <b>Application name：</b> SupayPayQueryResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class SupayPayQueryResponse extends SupayBaseResponse implements Response {

    /** 业务支付单号 */
    public String outTradeNo;

    /** 渠道侧服务单号 */
    public String serviceTradeNo;

    /** 支付类型 */
    public SupayPayType payType;

    /** 支付状态 */
    public SupayPayStatus payStatus;

    /** 支付时间 */
    private Date payTime;

    /** 支付用户id */
    protected String payUserId;

    /** 支付用户类型 */
    protected SupayPayUserType payUserType;

}