/*******************************************************************************
 * @(#)ChannelStats.java 2020年09月30日 08:49
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.stats;

import cn.org.supay.core.enums.SupayChannelType;
import lombok.Data;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <b>Application name：</b> ChannelStats.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年09月30日 08:49 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class ChannelStats {
    /** 渠道类型 */
    private SupayChannelType channelType;
    /** 总共支付次数 */
    public final AtomicLong totalCount	= new AtomicLong();
    /** 总共支付成功次数 */
    public final AtomicLong totalSuccess	= new AtomicLong();
    /** 总共支付失败次数 */
    public final AtomicLong totalFailed	= new AtomicLong();
    /** 总调用耗时 */
    public final AtomicLong	invokeCosts	= new AtomicLong();
    /** 平均耗时 */
    public long avgCost;

    public ChannelStats() {
    }

    public ChannelStats(SupayChannelType channelType) {
        this.channelType = channelType;
    }

    /**
     * 新增一次成功统计
     * @param cost
     */
    public synchronized void incrementSuccess(long cost) {
        totalCount.incrementAndGet();
        totalSuccess.incrementAndGet();
        invokeCosts.addAndGet(cost);
        this.avgCost = invokeCosts.get() / totalCount.get();
    }

    /**
     * 新增一次成功统计
     * @param cost
     */
    public synchronized void incrementFailed(long cost) {
        totalCount.incrementAndGet();
        totalFailed.incrementAndGet();
        invokeCosts.addAndGet(cost);
        this.avgCost = invokeCosts.get() / totalCount.get();
    }
}