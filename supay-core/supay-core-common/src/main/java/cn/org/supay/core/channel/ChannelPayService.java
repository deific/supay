/*******************************************************************************
 * @(#)ChannelPayService.java 2020年05月15日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel;

import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.channel.notify.ChannelNotifyData;
import cn.org.supay.core.channel.notify.ChannelNotifyHandler;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.context.SupayNotifyContext;
import cn.org.supay.core.enums.SupayChannelType;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> ChannelPayService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public interface ChannelPayService {
    /**
     * 注册自己的渠道服务
     */
    default void register() {
        SupayCoreConfig.registerPayChannelService(getSupportType(), this);
    }

    /**
     * 获取支持的渠道类型
     * @return
     */
    SupayChannelType getSupportType();

    /**
     * 检查通知并处理通知
     * @param notifyContext
     * @param handler
     * @return
     */
    default SupayNotifyContext checkAndHandleCallbackNotify(SupayNotifyContext notifyContext, ChannelNotifyHandler handler) {
        // 解析参数
        ChannelNotifyData notifyData = new ChannelNotifyData() {
            @Override
            public Map getNotifyOriginData() {
                // 解析form数据
                if (notifyContext.getFormParam() != null && !notifyContext.getFormParam().isEmpty()) {
                    return notifyContext.getFormParam();
                }

                // 解析流数据
                if (notifyContext.getBodyStr() != null) {
                    return new HashMap<String, String>(1) {{
                        put("body", notifyContext.getBodyStr());
                    }};
                }
                return null;
            }
        };
        ChannelNotifyHandler callbackHandler = handler;
        if (callbackHandler == null) {
            callbackHandler = SupayCoreConfig.getNotifyHandler(getSupportType());
        }

        boolean isOk = false;
        if (callbackHandler != null) {
            isOk = callbackHandler.handleNotify(notifyContext.getNotifyType(), notifyData);
        }
        return notifyContext.result("");
    }

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
     * 查询支付交易信息
     * @param ctx
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> queryPay(SupayContext<? extends Request, ? extends Response> ctx) {
        return ctx.fail("不支持该方法");
    }

    /**
     * 查询退款交易信息
     * @param ctx
     * @return
     */
    default SupayContext<? extends Request, ? extends Response> queryRefund(SupayContext<? extends Request, ? extends Response> ctx) {
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