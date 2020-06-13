/*******************************************************************************
 * @(#)MockPayChannelService.java 2020年06月13日 22:19
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.mock;

import cn.hutool.core.util.RandomUtil;
import cn.org.supay.core.channel.BasePayChannelService;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.enums.SupayChannelType;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Map;

/**
 * <b>Application name：</b> MockPayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年06月13日 22:19 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class MockPayChannelService implements BasePayChannelService {

    @Override
    public String getPayServiceName() {
        return "mockPayChannelService";
    }

    @Override
    public SupayChannelType getSupportType() {
        return SupayChannelType.MOCK;
    }

    @Override
    public String asyncNotifyCallback(Map formParam, InputStream body) {
        return null;
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
    public SupayContext<? extends Request, ? extends Response> queryTradeInfo(SupayContext<? extends Request, ? extends Response> ctx) {
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
        int successValue = RandomUtil.randomInt(1, 100);
        boolean isSuccess = successValue <= 95;
        if (isSuccess) {
            ctx.success("模拟成功");
        } else {
            ctx.fail("模拟操作失败");
        }
        return ctx;
    }
}