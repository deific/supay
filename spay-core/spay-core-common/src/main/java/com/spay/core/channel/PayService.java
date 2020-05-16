/*******************************************************************************
 * @(#)PayService.java 2020年05月15日 22:37
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel;

import com.spay.core.context.SpayContext;
import com.spay.core.data.SpayRequest;
import com.spay.core.data.SpayResponse;

/**
 * <b>Application name：</b> PayService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface PayService {

    /**
     * 直接支付
     * @param spayContext
     * @return
     */
    default SpayContext pay(SpayContext<? extends SpayRequest, ? extends SpayResponse> spayContext) {
        return SpayContext.fail(spayContext, "不支持该方法");
    }

    /**
     * 确认支付
     * @param spayContext
     * @return
     */
    default SpayContext confirm(SpayContext<? extends SpayRequest, ? extends SpayResponse> spayContext) {
        return SpayContext.fail(spayContext, "不支持该方法");
    }

    /**
     * 退款
     * @param spayContext
     * @return
     */
    default SpayContext refund(SpayContext<? extends SpayRequest, ? extends SpayResponse> spayContext) {
        return SpayContext.fail(spayContext, "不支持该方法");
    }

    /**
     * 批量查询支付状态
     * @param spayContext
     * @return
     */
    default SpayResponse queryTradeInfo(SpayContext<? extends SpayRequest, ? extends SpayResponse> spayContext) {
        return SpayContext.fail(spayContext, "不支持该方法");
    }

    /**
     * 发送红包
     * @param spayContext
     * @return
     */
    default SpayContext sendRedPackage(SpayContext<? extends SpayRequest, ? extends SpayResponse> spayContext) {
        return SpayContext.fail(spayContext, "不支持该方法");
    }
}