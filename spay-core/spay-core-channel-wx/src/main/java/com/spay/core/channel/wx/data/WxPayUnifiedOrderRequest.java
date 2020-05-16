/*******************************************************************************
 * @(#)WxPayUnifiedOrderRequest.java 2020年05月16日 09:47
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.data;

import com.spay.core.converter.SpayConverter;
import com.spay.core.data.SpayRequest;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String mch_id;
    private String sub_appid;
    private String sub_mch_id;
    private String device_info;
    private String nonce_str;
    private String sign;
    private String sign_type;
    private String body;
    private String detail;
    private String attach;
    private String out_trade_no;
    private String fee_type;
    private String total_fee;
    private String spbill_create_ip;
    private String time_start;
    private String time_expire;
    private String goods_tag;
    private String notify_url;
    private String trade_type;
    private String product_id;
    private String limit_pay;
    private String openid;
    private String sub_openid;
    private String receipt;
    private String scene_info;
    private String profit_sharing;
}