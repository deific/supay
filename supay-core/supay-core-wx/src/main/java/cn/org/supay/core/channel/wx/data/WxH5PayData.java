/*******************************************************************************
 * @(#)WxTradeType.java 2017年04月18日 17:33
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <b>Application name：</b> WxH5PayData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class WxH5PayData extends WxPayData implements Serializable {
    /** 微信appid */
    private String appId;
    /** 时间戳 */
    private String mchId;
    /** 微信预支付id */
    private String prepayId;
    /** 时间戳 */
    private String packageStr;
    /** 时间戳 */
    private String timeStamp;
    /** 时间戳 */
    private String nonceStr;
}
