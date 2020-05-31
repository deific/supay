/*******************************************************************************
 * @(#)WxBaseRequest.java 2020年05月16日 22:24
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import cn.hutool.core.util.StrUtil;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;
import cn.org.supay.core.utils.BeanUtils;
import cn.org.supay.core.utils.SignUtils;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <b>Application name：</b> WxBaseRequest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 22:24 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class WxPayBaseRequest implements Request {
    /** 微信分配的应用ID（公众号、小程序、app） */
    @XmlField("appid")
    private String appid;
    /** 微信支付分配的商户号 */
    @XmlField("mch_id")
    protected String mchId;

    /** 服务商模式下的子商户应用（公众号、小程序、app）ID */
    @XmlField("sub_appid")
    protected String subAppId;

    /** 服务商模式下的子商户号 微信支付分配的子商户号，开发者模式下必填 */
    @XmlField("sub_mch_id")
    protected String subMchId;
    /** 随机字符串，不长于32位。推荐随机数生成算法 */
    @XmlField("nonce_str")
    protected String nonceStr;

    /** 签名 签名，详见签名生成算法 */
    @XmlField("sign")
    protected String sign;

    @Override
    public SupayContext<? extends Request, ? extends Response> checkAndSign(SupayContext<? extends Request, ? extends Response> ctx) {
        // 参数校验
        if (StrUtil.isBlank(appid)) {
            ctx.fail("appId不能为空");
            return ctx;
        }

        // 签名
        String sign = SignUtils.signForMap(BeanUtils.xmlBean2Map(this), ctx.getChannelConfig().getMchSecretKey());
        this.setSign(sign);
        return ctx;
    }
}