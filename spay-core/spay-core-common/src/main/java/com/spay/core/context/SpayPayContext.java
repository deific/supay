/*******************************************************************************
 * @(#)SpayPayContext.java 2020年05月18日 23:16
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.context;

import com.spay.core.data.Request;
import com.spay.core.data.Response;
import com.spay.core.enums.SpayPayType;
import com.spay.core.enums.SpayTradeType;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <b>Application name：</b> SpayPayContext.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月18日 23:16 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class SpayPayContext<PR extends Request, PS extends Response> extends SpayContext<PR, PS> {
    /** 支付请求交易类型 */
    protected SpayTradeType tradeType;
    /** 支付方式 */
    protected SpayPayType payType;
}