/*******************************************************************************
 * @(#)WxPayBaseResponse.java 2020年05月16日 22:24
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;
import cn.org.supay.core.utils.BeanUtils;
import cn.org.supay.core.utils.SignUtils;
import lombok.Data;

import java.util.Map;

/**
 * <b>Application name：</b> WxPayBaseResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 22:24 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class WxPayBaseResponse<T extends WxPayData> implements Response {
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

    /**
     * 不同支付交易类型下支付数据封装,便于更清晰的区分不同支付类型的数据
     * @link {WxPayData}
     */
    protected T payData;

    /**
     * 校验返回结果签名
     */
    @Override
    public SupayContext<Request, Response> checkResult(SupayContext ctx) {
        //校验返回结果签名
        Map<String, Object> map = BeanUtils.xmlBean2Map(this);
        if (getSign() != null && !SignUtils.checkSignForMap(map, ctx.getChannelConfig().getMchSecretKey())) {
            ctx.fail("微信返回报文签名校验失败");
        }

        //校验结果是否成功
        if (!"SUCCESS".equalsIgnoreCase(getReturnCode())
                || !"SUCCESS".equalsIgnoreCase(getResultCode())) {
            StringBuilder errorMsg = new StringBuilder("[微信接口返回]");
            if (getReturnCode() != null) {
                errorMsg.append("返回代码：").append(getReturnCode());
            }
            if (getReturnMsg() != null) {
                errorMsg.append("，返回信息：").append(getReturnMsg());
            }
            if (getResultCode() != null) {
                errorMsg.append("，结果代码：").append(getResultCode());
            }
            if (getErrCode() != null) {
                errorMsg.append("，错误代码：").append(getErrCode());
            }
            if (getErrCodeDes() != null) {
                errorMsg.append("，错误详情：").append(getErrCodeDes());
            }
            ctx.fail(errorMsg.toString());
        }
        ctx.success("成功");
        return ctx;
    }
}