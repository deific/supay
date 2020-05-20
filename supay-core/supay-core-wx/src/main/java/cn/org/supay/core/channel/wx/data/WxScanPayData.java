/*******************************************************************************
 * @(#)WxTradeType.java 2017年04月18日 17:33
 * Copyright 2017 明医众禾科技（北京）有限责任公司. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * <b>Application name：</b> WxScanData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class WxScanPayData extends WxPayData implements Serializable {
    /** 微信预支付id */
    private String prepayId;
    /** 微信二维码url */
    private String codeUrl;
    /** 交易id */
    private String tradeId;
}
