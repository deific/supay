/*******************************************************************************
 * @(#)JdkProxy.java 2020年09月26日 22:08
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.proxy;

import cn.org.supay.core.channel.ChannelPayService;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.context.SupayContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * <b>Application name：</b> JdkProxy.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年09月26日 22:08 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
public class JdkProxy extends ChannelPayProxy implements InvocationHandler {

    public JdkProxy(ChannelPayService targetService) {
        super(targetService);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        log.debug("[jdk调用][{}#{}]正在调用服务...", this.targetService.getClass().getSimpleName(), method.getName());
        SupayContext<? extends Request, ? extends Response> ctx = (SupayContext<? extends Request, ? extends Response>)args[0];
        try {
            this.beforeInvoke(ctx);
            //方法执行，参数：target 目标对象 arr参数数组
            ctx = (SupayContext<? extends Request, ? extends Response>) method.invoke(targetService, args);
            this.afterInvoke(ctx);
        } catch (Exception e) {
            log.error("[调用]服务调用异常：", e);
            this.compalete(ctx);
        }
        return ctx;
    }

    /**
     * 定义获取代理对象方法
     * @return
     */
    @Override
    public ChannelPayService getProxyService() {
        //JDK动态代理只能针对实现了接口的类进行代理，newProxyInstance 函数所需参数就可看出
        return (ChannelPayService) Proxy.newProxyInstance(this.targetService.getClass().getClassLoader(),
                this.targetService.getClass().getInterfaces(), this);
    }
}