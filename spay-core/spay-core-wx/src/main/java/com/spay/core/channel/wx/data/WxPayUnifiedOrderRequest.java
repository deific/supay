/*******************************************************************************
 * @(#)WxPayUnifiedOrderRequest.java 2020年05月16日 09:47
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.data;

import com.spay.core.annotation.XmlField;
import com.spay.core.data.SpayRequest;
import lombok.Builder;
import lombok.Data;

/**
 * <b>Application name：</b> WxPayUnifiedOrderRequest.java <br>
 * <b>Application describing： </b> 微信支付统一下单接口参数对象 <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 09:47 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Builder
@Data
public class WxPayUnifiedOrderRequest extends SpayRequest {
    private String appid;
    @XmlField("mch_id")
    private String mchId;
    @XmlField("sub_appid")
    private String subAppId;
    @XmlField("sub_mch_id")
    private String subMchId;
    @XmlField("device_info")
    private String deviceInfo;
    @XmlField("nonce_str")
    private String nonceStr;
    private String sign;
    @XmlField("signType")
    private String sign_type;
    private String body;
    private String detail;
    private String attach;
    @XmlField("out_trade_no")
    private String outTradeNo;
    @XmlField("fee_type")
    private String feeType;
    @XmlField("total_fee")
    private String totalFee;
    @XmlField("spbill_create_ip")
    private String spbillCreateIp;
    @XmlField("time_start")
    private String timeStart;
    @XmlField("time_expire")
    private String timeExpire;
    @XmlField("goods_tag")
    private String goodsTag;
    @XmlField("notify_url")
    private String notifyUrl;
    @XmlField("trade_type")
    private String tradeType;
    @XmlField("product_id")
    private String productId;
    @XmlField("limit_pay")
    private String limitPay;
    @XmlField("openid")
    private String openid;
    @XmlField("sub_openid")
    private String subOpenid;
    private String receipt;
    @XmlField("scene_info")
    private String sceneInfo;
    @XmlField("profit_sharing")
    private String profitSharing;
}