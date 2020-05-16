/*******************************************************************************
 * @(#)SpayContext.java 2020年05月15日 22:51
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.context;

import com.spay.core.Spay;
import com.spay.core.config.SpayChannelConfig;
import com.spay.core.data.SpayRequest;
import com.spay.core.data.SpayResponse;
import com.spay.core.enums.SpayPayType;
import com.spay.core.enums.SpayTradeType;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Builder
@NoArgsConstructor
public class SpayContext<R extends SpayRequest, S extends SpayResponse> {
    /** 交易流水号 */
    protected String tradeId;
    /** 支付请求交易类型 */
    protected SpayTradeType tradeType;
    /** 支付方式 */
    protected SpayPayType payType;
    /** 支付渠道参数 */
    protected SpayChannelConfig channelConfig;
    /** 支付请求参数 */
    protected  R request;
    /** 支付结果 */
    protected S response;

    /**
     * 失败
     * @param ctx
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> T fail(SpayContext<? extends SpayRequest, ? extends SpayResponse> ctx, String msg) {
        if (ctx.response == null) {

        }
        ctx.response.setResultMsg(msg);
        ctx.response.setSuccess(false);
        return (T) ctx;
    }

    /**
     * 是否有错误
     * @return
     */
    public boolean hasError() {
        return response != null && !response.isSuccess();
    }

    /**
     * 设置结果
     * @param s
     */
    public void setResponse(S s) {
        this.response = s;
    }
}