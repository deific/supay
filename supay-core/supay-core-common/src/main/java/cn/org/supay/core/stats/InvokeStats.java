/*******************************************************************************
 * @(#)InvokeStats.java 2020年09月28日 22:40
 * Copyright 2020 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.stats;

import lombok.Data;

import java.util.Date;

/**
 * <b>Application name：</b> InvokeStats.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 明医众禾科技（北京）有限责任公司 版权所有。<br>
 * <b>Company：</b> 明医众禾科技（北京）有限责任公司 <br>
 * <b>@Date：</b> 2020年09月28日 22:40 <br>
 * <b>@author：</b> <a href="mailto:chensg@miyzh.com"> chensg </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class InvokeStats {
    /** 调用层级 */
    private int invokeLevel = 0;
    /** 调用服务 */
    private String invokeService;
    /** 调用耗时 */
    private long invokeCost;
    /** 调用开始时间 */
    private Date startTime;
    /** 调用结束时间 */
    private Date endTime;
    /** 下一层调用 */
    private InvokeStats nextInvokeStats;


}