/*******************************************************************************
 * @(#)WxPayUnifiedOrderResponse.java 2020年05月16日 09:48
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import cn.org.supay.core.annotation.XmlField;
import lombok.Data;
import lombok.ToString;

/**
 * <b>Application name：</b> WxPayUnifiedOrderResponse.java <br>
 * <b>Application describing： </b> 微信支付统一下单接口响应结果 <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:48 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@ToString(callSuper = true)
public class WxPayUnifiedOrderResponse<T extends WxPayData> extends WxPayBaseResponse {

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