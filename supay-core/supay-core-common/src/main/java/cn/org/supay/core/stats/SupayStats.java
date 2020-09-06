/*******************************************************************************
 * @(#)SupayStats.java 2020年09月6日 08:49
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.stats;

import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <b>Application name：</b> SupayStats.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年09月6日 08:49 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class SupayStats {
    /** 总共支付次数 */
    public final AtomicLong totalCount	= new AtomicLong();
    /** 总共支付成功次数 */
    public final AtomicLong totalSuccess	= new AtomicLong();
    /** 总共支付失败次数 */
    public final AtomicLong totalFailed	= new AtomicLong();

    /** 总调用耗时 */
    public final AtomicLong	invokeCosts	= new AtomicLong();
    /** 渠道总调用耗时 */
    public final AtomicLong	channelInvokeCosts	= new AtomicLong();
}