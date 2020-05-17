/*******************************************************************************
 * @(#)WxTradeType.java 2017年04月18日 17:33
 * Copyright 2017 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.data;

import java.io.Serializable;

/**
 * <b>Application name：</b> WxH5PayData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class WxH5PayData implements Serializable {
    /** 微信预支付id */
    private String prepayId;
    /** 微信预支付id */
    private String appId;
    /** 时间戳 */
    private String mchId;
    /** 时间戳 */
    private String packageStr;
    /** 时间戳 */
    private String timeStamp;
    /** 时间戳 */
    private String nonceStr;
    /** 时间戳 */
    private String sign;
}
