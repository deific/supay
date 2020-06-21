/*******************************************************************************
 * @(#)SupayRequest.java 2020年05月15日 22:44
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

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
public class SupayPayRequest implements Request {
    /** 业务支付单号 */
    private String tradeNo;
    /** 业务支付单号 */
    private String tradeName;
    /** 交易金额 */
    private BigDecimal amount;
    /** 异步回调通知url */
    private String notifyUrl;
    /** 同步返回url */
    private String returnUrl;
    /** 支付参数 */
    private String payParam;


    /**
     * 转换为上下文
     * @param appId
     * @param isSandbox
     * @return
     */
    @Override
    public SupayContext<? extends Request, ? extends Response> toContext(String appId, boolean isSandbox) {
        SupayContext context = SupayContext.buildContext(SupayCoreConfig.getChannelConfig(appId), this, isSandbox);
        context.setAggregate(true);
        return context;
    }
}