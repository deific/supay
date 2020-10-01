/*******************************************************************************
 * @(#)CglibProxy.java 2020年09月26日 22:08
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.proxy;

import cn.org.supay.core.channel.ChannelPayService;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

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
public class CglibProxy extends ChannelPayProxy implements MethodInterceptor {

    public CglibProxy(ChannelPayService proxyService) {
        super(proxyService);
        log.debug("[代理]jdk代理：{}", targetService.getClass().getSimpleName());
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) {
        return invoke(method, args);
    }

    /**
     * 定义获取代理对象方法
     * @return
     */
    @Override
    public ChannelPayService newProxyInstance() {
        //为目标对象target赋值
        Enhancer enhancer = new Enhancer();
        //设置父类,因为Cglib是针对指定的类生成一个子类，所以需要指定父类
        enhancer.setSuperclass(this.targetService.getClass());
        // 设置回调
        enhancer.setCallback(this);
        //创建并返回代理对象
        ChannelPayService result = (ChannelPayService) enhancer.create();
        return result;
    }

}