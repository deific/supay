/*******************************************************************************
 * @(#)SupayRequest.java 2020年05月15日 22:44
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.channel.aggregate.context.AggregateContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayPayType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <b>Application name：</b> SuPayRequest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:44 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class SupayBaseRequest implements Request {
    /** 其他接口配置参数 */
    protected Map<String, Object> optionParams;

    /**
     * 设置其他参数
     * @param paramName
     * @param paramValue
     * @return
     */
    public SupayBaseRequest setParam(String paramName, Object paramValue) {
        optionParams.put(paramName, paramValue);
        return this;
    }
}