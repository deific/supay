/*******************************************************************************
 * @(#)WxPayUnifiedOrderResponse.java 2020年05月16日 09:48
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.aggregate.data.*;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayPayStatus;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.enums.SupayPayUserType;
import cn.org.supay.core.utils.BeanUtils;
import cn.org.supay.core.utils.SignUtils;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

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
public class WxPayUnifiedOrderResponse<T extends WxPayData> extends WxPayBaseResponse implements AggregateResponseConvert {
    /** 用户标识，支付码支付时，下单接口会返回。用户在商户appid 下的唯一标识 */
    private String openid;
    /** 用户是否关注公众账号，仅在公众账号类型支付有效，取值范围：Y或N;Y-关注;N-未关注 */
    @XmlField("is_subscribe")
    private String isSubscribe;

    /** 交易类型，取值为：JSAPI，NATIVE，APP等 */
    @XmlField("trade_type")
    private String tradeType;

    /** 付款银行，采用字符串类型的银行标识，值列表详见银行类型 */
    @XmlField("bank_type")
    private String bankType;

    /** 标价金额，单位为该币种最小计算单位，只能为整数 */
    @XmlField("total_fee")
    private Integer totalFee;

    /** 标价币种 */
    @XmlField("fee_type")
    private String feeType;

    /** 用户支付金额 */
    @XmlField("cash_fee")
    private Integer cashFee;

    /** 用户支付金额币种 */
    @XmlField("cash_fee_type")
    private String cashFeeType;

    /** 附加数据 */
    private String attach;

    /** 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时 */
    @XmlField("prepay_id")
    private String prepayId;

    /** 商户订单号 */
    @XmlField("out_trade_no")
    private String outTradeNo;

    /** 微信支付订单号(付款码支付等支付成功时同步返回) */
    @XmlField("transaction_id")
    private String transactionId;


    /** trade_type为NATIVE时有返回，用于生成二维码，展示给用户进行扫码支付 */
    @XmlField("code_url")
    private String codeURL;

    /** trade_type为H5时有返回 支付跳转链接 */
    @XmlField("mweb_url")
    private String mwebUrl;

    /** 订单生成时间，格式为yyyyMMddHHmmss，如2009年12月25日9点10分10秒表示为20091225091010 */
    @XmlField("time_end")
    private String timeEnd;

    /** 汇率 */
    private String rate;

    @Override
    public SupayBaseResponse convertResponse(SupayContext ctx) {
        SupayPayResponse payResponse = null;
        SupayPayType payType = ctx.getPayType();
        switch (payType) {
            // h5支付场景信息
            case WX_H5_PAY:
                payResponse = SupayH5PayResponse.builder()
                        .redirectBody(getMwebUrl())
                        .payStatus(SupayPayStatus.NO_PAY)
                        .build();
                break;
            case WX_MP_PAY:
                Map<String, String> mpParam = new HashMap<>();
                // APP支付参数
                mpParam.put("appid", this.appid);
                mpParam.put("partnerid", ctx.getChannelConfig().getMchId());
                mpParam.put("prepayid", this.prepayId);
                mpParam.put("noncestr", this.nonceStr);
                mpParam.put("package", "Sign=WXPay");
                mpParam.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                mpParam.put("sign", SignUtils.signForMap(BeanUtils.xmlBean2Map(mpParam), ctx.getChannelConfig().getMchSecretKey()));

                payResponse = SupayMpPayResponse.builder()
                        .redirectBody(JSONUtil.toJsonStr(mpParam))
                        .redirectType(RedirectType.JSON_BODY)
                        .payStatus(SupayPayStatus.NO_PAY)
                        .build();
                break;
            case WX_APP_PAY:
                Map<String, String> appParam = new HashMap<>();
                // APP支付参数
                appParam.put("appid", this.appid);
                appParam.put("partnerid", ctx.getChannelConfig().getMchId());
                appParam.put("prepayid", this.prepayId);
                appParam.put("noncestr", this.nonceStr);
                appParam.put("package", "Sign=WXPay");
                appParam.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                appParam.put("sign", SignUtils.signForMap(BeanUtils.xmlBean2Map(appParam), ctx.getChannelConfig().getMchSecretKey()));

                payResponse = SupayAppPayResponse.builder()
                        .redirectBody(JSONUtil.toJsonStr(appParam))
                        .redirectType(RedirectType.JSON_BODY)
                        .payStatus(SupayPayStatus.NO_PAY)
                        .build();
                break;
            case WX_SCAN_PAY:
                payResponse = SupayScanPayResponse.builder()
                        .qrCodeUrl(this.codeURL)
                        .payStatus(SupayPayStatus.NO_PAY)
                        .build();
                break;
            case WX_FACE_PAY:
                break;
            case WX_MICRO_PAY:
                payResponse = SupayMicroPayResponse.builder()
                        .payStatus(this.checkResult()?SupayPayStatus.PAY_SUCCESS:
                                StrUtil.containsAny(this.errCode, "USERPAYING", "SYSTEMERROR")?
                                        SupayPayStatus.PAY_PROCESSING:SupayPayStatus.PAY_FAIL)
                        .attach(attach)
                        .outTradeNo(outTradeNo)
                        .serviceTradeNo(transactionId)
                        .payUserId(openid)
                        .payUserType(SupayPayUserType.WX_OPEN_ID)
                        .payTime(StrUtil.isNotBlank(timeEnd)? DateUtil.parse(timeEnd, "yyyyMMddHHmmss"):null)
                        .build();
                break;
            default:
                payResponse = SupayPayResponse.builder().build();
        }
        payResponse.setPayType(payType);
        payResponse.setResultCode(checkReturn()?this.getErrCode():this.getReturnCode());
        payResponse.setResultMsg(checkReturn()?this.getErrCodeDes():this.getReturnMsg());
        payResponse.setSuccess(this.checkResult());
        return payResponse;
    }
}