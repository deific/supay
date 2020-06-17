/*******************************************************************************
 * @(#)ChannelPayService.java 2020年05月15日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel;

import cn.hutool.core.io.IoUtil;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.channel.notify.ChannelNotifyData;
import cn.org.supay.core.channel.notify.ChannelNotifyHandler;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayChannelType;

import java.io.InputStream;
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
        SupayCoreConfig.registerPayService(getSupportType(), this);
    }

    /**
     * 获取支持的渠道类型
     * @return
     */
    SupayChannelType getSupportType();

    /**
     * 异步通知处理回调处理
     * @param formParam 表单数据
     * @param body 请求体参数
     * @return
     */
    default String asyncNotifyCallback(Map formParam, InputStream body) {
        // 解析参数
        ChannelNotifyData notifyData = new ChannelNotifyData() {
            @Override
            public Map getNotifyOriginData() {
                // 解析form数据
                if (formParam != null && !formParam.isEmpty()) {
                    return formParam;
                }

                // 解析流数据
                if (body != null) {
                    return new HashMap<String, byte[]>(1) {{
                        put("body", IoUtil.readBytes(body));
                    }};
                }
                return null;
            }
        };

        ChannelNotifyHandler callbackHandler = SupayCoreConfig.getNotifyHandler(getSupportType());
        if (callbackHandler != null) {
            return callbackHandler.handle(notifyData, this);
        }
        return "不支持该通知";
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