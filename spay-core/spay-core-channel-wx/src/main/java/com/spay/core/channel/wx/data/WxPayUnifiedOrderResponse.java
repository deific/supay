/*******************************************************************************
 * @(#)WxPayUnifiedOrderResponse.java 2020年05月16日 09:48
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.data;

import com.spay.core.data.SpayResponse;
import lombok.Builder;
import lombok.Data;

/**
 * <b>Application name：</b> WxPayUnifiedOrderResponse.java <br>
 * <b>Application describing： </b> 微信支付统一下单接口响应结果 <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 09:48 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class WxPayUnifiedOrderResponse extends SpayResponse {
    private String appid;
}