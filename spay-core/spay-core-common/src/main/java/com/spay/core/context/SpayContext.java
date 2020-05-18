/*******************************************************************************
 * @(#)SpayContext.java 2020年05月15日 22:51
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.context;

import com.spay.core.channel.filter.FilterChain;
import com.spay.core.channel.filter.SpayFilter;
import com.spay.core.channel.filter.SpayFilterChain;
import com.spay.core.config.SpayChannelConfig;
import com.spay.core.config.SpayConfig;
import com.spay.core.converter.SpayConverter;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import com.spay.core.enums.SpayPayType;
import com.spay.core.enums.SpayTradeType;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * <b>Application name：</b> SpayContext.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 22:51 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder(builderMethodName = "ctxBuilder")
@ToString(callSuper = true)
public class SpayContext<R extends Request, S extends Response> extends SpayFilterChain {
    /** 交易流水号 */
    protected String tradeId;

    /** 支付渠道参数 */
    protected SpayChannelConfig channelConfig;
    /** 开始时间 */
    private Date startTime;
    /** 结束时间 */
    private Date endTime;
    /** 支付请求参数 */
    protected  R request;
    /** 支付结果 */
    protected S response;
    /** 交易是否成功 */
    protected boolean success = true;
    /** 交易信息 */
    protected String msg;

    /**
     * 成功
     * @param msg
     * @return
     */
    public SpayContext success(String msg) {
        this.setMsg(msg);
        this.setSuccess(true);
        return this;
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public SpayContext fail(String msg) {
        this.setMsg(msg);
        this.setSuccess(false);
        return this;
    }

    /**
     * 是否有错误
     * @return
     */
    public boolean hasError() {
        return success;
    }

    /**
     * 转换为请求字符串
     * @return
     */
    public String toRequestStr() {
        SpayConverter converter = SpayConfig.getApiParamConverter(this.channelConfig.getChannelType());
        return converter.convert(this.getRequest());
    }

    /**
     * 解析响应字符串为对象实例
     * @param respStr
     * @param targetClass
     * @return
     */
    public Response parseResponseStr(String respStr, Class<? extends Response> targetClass) {
        SpayConverter converter = SpayConfig.getApiParamConverter(this.channelConfig.getChannelType());
        this.setResponse((S) converter.convert(respStr, targetClass));
        // 检查结果
        this.getResponse().checkResult(this);
        return this.response;
    }

    /**
     * 耗时
     * @return
     */
    public long duration() {
        return this.endTime.getTime() - this.startTime.getTime();
    }
}