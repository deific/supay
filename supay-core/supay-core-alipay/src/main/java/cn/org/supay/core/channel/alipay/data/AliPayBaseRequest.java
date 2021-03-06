/*******************************************************************************
 * @(#)AliPayBaseRequest.java 2017年04月18日 17:33
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.data;

import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.enums.SupayPayType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> AliPayBaseRequest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
@NoArgsConstructor
public abstract class AliPayBaseRequest implements Request {
    /** 支付类型 */
    protected SupayPayType payType;
    protected String subject;
    protected String outTradeNo;
    protected String totalAmount;
    /** 其他非必要配置参数 */
    protected Map<String, Object> optionParams = new HashMap<>();

    /**
     * 设置其他参数
     * @param paramName
     * @param paramValue
     * @return
     */
    public AliPayBaseRequest setParam(String paramName, Object paramValue) {
        optionParams.put(paramName, paramValue);
        return this;
    }
}