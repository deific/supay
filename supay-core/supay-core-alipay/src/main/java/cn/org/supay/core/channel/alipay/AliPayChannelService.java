/*******************************************************************************
 * @(#)AliPayChannelService.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay;

import cn.org.supay.core.channel.BasePayChannelService;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;

/**
 * <b>Application name：</b> AliPayChannelService.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月25日 19:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class AliPayChannelService implements BasePayChannelService {
    @Override
    public String getPayServiceName() {
        return "aliPayChannelService";
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> pay(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> confirm(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> refund(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> queryTradeInfo(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }

    @Override
    public SupayContext<? extends Request, ? extends Response> sendRedPackage(SupayContext<? extends Request, ? extends Response> ctx) {
        return null;
    }
}