/*******************************************************************************
 * @(#)AggregateConvert.java 2020年05月29日 12:26
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

/**
 * <b>Application name：</b> AggregateConvert.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface AggregateRequestConvert {
    /**
     * 转换聚合请求参数未指定渠道参数类型
     * @param request
     * @param <T>
     * @return
     */
    <T> T convertRequest(SupayBaseRequest request);
}