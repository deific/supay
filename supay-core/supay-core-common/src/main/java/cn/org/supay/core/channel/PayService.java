/*******************************************************************************
 * @(#)PayService.java 2020年05月15日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel;

import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;

/**
 * <b>Application name：</b> PayService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface PayService {

    /**
     * 直接支付
     * @param ctx
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        return ctx.fail("不支持该方法");
    }

    /**
     * 确认支付
     * @param ctx
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> confirm(SupayContext<? extends Request, ? extends Response> ctx) {
        return ctx.fail("不支持该方法");
    }

    /**
     * 退款
     * @param ctx
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        return ctx.fail("不支持该方法");
    }

    /**
     * 批量查询交易信息
     * @param ctx
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> queryTradeInfo(SupayContext<? extends Request, ? extends Response> ctx) {
        return ctx.fail("不支持该方法");
    }

    /**
     * 发送红包
     * @param ctx
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> sendRedPackage(SupayContext<? extends Request, ? extends Response> ctx) {
        return ctx.fail("不支持该方法");
    }
}