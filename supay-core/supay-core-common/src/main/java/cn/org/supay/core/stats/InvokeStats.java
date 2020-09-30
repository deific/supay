/*******************************************************************************
 * @(#)InvokeStats.java 2020年09月30日 08:49
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.stats;

import cn.org.supay.core.enums.SupayChannelType;
import lombok.Data;

import java.util.Date;

/**
 * <b>Application name：</b> InvokeStats.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年09月30日 08:49 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class InvokeStats {
    /** 渠道类型 */
    private SupayChannelType channelType;
    /** 调用层级 */
    private int invokeLevel = 0;
    /** 调用服务 */
    private String invokeService;
    /** 调用服务方法 */
    private String invokeMethod;
    /** 调用耗时 */
    private long invokeCost;
    /** 调用开始时间 */
    private Date startTime;
    /** 调用结束时间 */
    private Date endTime;
    /** 下一层调用 */
    private InvokeStats nextInvoke;

    public InvokeStats() {

    }

    public InvokeStats(SupayChannelType channelType, int invokeLevel, String invokeService, String invokeMethod, Date startTime) {
        this.channelType = channelType;
        this.invokeLevel = invokeLevel;
        this.invokeService = invokeService;
        this.invokeMethod = invokeMethod;
        this.startTime = startTime;
    }
}