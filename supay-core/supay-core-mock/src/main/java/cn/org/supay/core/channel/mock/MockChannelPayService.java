/*******************************************************************************
 * @(#)MockPayChannelService.java 2020年06月13日 22:19
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.mock;

import cn.hutool.core.util.RandomUtil;
import cn.org.supay.core.channel.BaseChannelPayService;
import cn.org.supay.core.channel.aggregate.data.SupayBaseResponse;
import cn.org.supay.core.channel.notify.ChannelNotifyHandler;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.context.SupayNotifyContext;
import cn.org.supay.core.enums.SupayChannelType;
import com.github.jsonzou.jmockdata.JMockData;
import com.github.jsonzou.jmockdata.MockConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Map;

/**
 * <b>Application name：</b> MockChannelPayService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年06月13日 22:19 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class MockChannelPayService implements BaseChannelPayService {

    private MockConfig mockConfig = new MockConfig()
            // 全局配置
            .setEnabledStatic(false)
            // 某些字段（名等于integerNum的字段、包含float的字段、double开头的字段）配置
            .subConfig("success","*code*","msg")
            .booleanSeed(true)
            .intRange(0, 0)
            .stringSeed("成功")
            .globalConfig();

    @Override
    public SupayChannelType getSupportType() {
        return SupayChannelType.MOCK;
    }

    @Override
    public SupayNotifyContext checkAndHandleCallbackNotify(SupayNotifyContext notifyCtx, ChannelNotifyHandler handler) {
        return notifyCtx;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        return randomSuccess(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> confirm(SupayContext<? extends Request, ? extends Response> ctx) {
        return randomSuccess(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        return randomSuccess(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryPay(SupayContext<? extends Request, ? extends Response> ctx) {
        return randomSuccess(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryRefund(SupayContext<? extends Request, ? extends Response> ctx) {
        return randomSuccess(ctx);
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> sendRedPackage(SupayContext<? extends Request, ? extends Response> ctx) {
        return randomSuccess(ctx);
    }

    /**
     * 随机95%成功率
     * @return
     */
    private SupayContext<? extends Request, ? extends Response> randomSuccess(SupayContext<? extends Request, ? extends Response> ctx) {

        Class respClass = ctx.getRequest().getRespClass();
        Response response = (Response) JMockData.mock(respClass, mockConfig);
        ctx.setResponse(response);

        int successValue = RandomUtil.randomInt(1, 100);
        boolean isSuccess = successValue <= 98;
        if (isSuccess) {
            ctx.success("模拟成功支付");
        } else {
            ctx.fail("模拟失败支付");
        }
        return ctx;
    }
}