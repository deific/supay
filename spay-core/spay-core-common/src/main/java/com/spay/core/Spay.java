/*******************************************************************************
 * @(#)SPay.java 2020年05月15日 22:36
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core;

import com.spay.core.context.SpayContext;
import com.spay.core.data.Request;
import com.spay.core.data.Response;
import com.spay.core.pay.SpayCore;

/**
 * <b>Application name：</b> SPay.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 22:36 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class Spay {

    /** 私有构造函数 */
    private Spay() {}

    /**
     * 直接支付
     * @param spayContext
     * @return
     */
    public static SpayContext<? extends Request, ? extends Response> pay(SpayContext<? extends Request, ? extends Response> spayContext) {
        return SpayCore.pay(spayContext);
    }
}