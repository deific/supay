/*******************************************************************************
 * @(#)WxPayUnifiedOrderResponse.java 2020年05月16日 09:48
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.data;

import com.spay.core.annotation.XmlField;
import lombok.Data;
import lombok.ToString;

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
@ToString(callSuper = true)
public class WxPayUnifiedOrderResponse extends WxPayBaseResponse {

    /** 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时 */
    @XmlField("prepay_id")
    private String prepayId;

    /** 交易类型，取值为：JSAPI，NATIVE，APP等 */
    @XmlField("trade_type")
    private String tradeType;

    /** trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付 */
    @XmlField("code_url")
    private String codeURL;

    /** 支付跳转链接 */
    @XmlField("mweb_url")
    private String mwebUrl;
}