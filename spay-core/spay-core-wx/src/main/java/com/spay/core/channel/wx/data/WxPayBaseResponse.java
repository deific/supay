/*******************************************************************************
 * @(#)WxPayBaseResponse.java 2020年05月16日 22:24
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.wx.data;

import com.spay.core.annotation.XmlField;
import com.spay.core.data.Response;
import lombok.Data;

/**
 * <b>Application name：</b> WxPayBaseResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 22:24 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class WxPayBaseResponse extends Response {
    /** 返回状态码 */
    @XmlField("return_code")
    protected String returnCode;
    /** 返回信息 */
    @XmlField("return_msg")
    protected String returnMsg;

    /** 当return_code为SUCCESS的时候，还会包括以下字段：*/
    /** 业务结果 */
    @XmlField("result_code")
    protected String resultCode;
    /** 错误代码 */
    @XmlField("err_code")
    protected String errCode;
    /** 错误代码描述 */
    @XmlField("err_code_des")
    protected String errCodeDes;
    /** 应用ID */
    @XmlField("appid")
    protected String appid;
    /** 商户号 */
    @XmlField("mch_id")
    protected String mchId;
    /** 服务商模式下的子公众账号ID */
    @XmlField("sub_appid")
    protected String subAppId;
    /** 服务商模式下的子商户号 */
    @XmlField("sub_mch_id")
    protected String subMchId;
    /** 随机字符串 */
    @XmlField("nonce_str")
    protected String nonceStr;
    /** 签名 */
    @XmlField("sign")
    protected String sign;
}