/*******************************************************************************
 * @(#)WxPayUnifiedOrderRequest.java 2020年05月16日 09:47
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.aggregate.data.*;
import cn.org.supay.core.enums.SupayPayType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * <b>Application name：</b> WxPayUnifiedOrderRequest.java <br>
 * <b>Application describing： </b> 微信支付统一下单接口参数对象 <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:47 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class WxPayUnifiedOrderRequest extends WxPayBaseRequest implements AggregateRequestConvert {
    @XmlField("device_info")
    private String deviceInfo;
    @XmlField("nonce_str")
    private String nonceStr;
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

    /** 交易起始时间 */
    @XmlField("time_start")
    private String timeStart;
    /** 交易结束时间 */
    @XmlField("time_expire")
    private String timeExpire;
    /** 订单优惠标记 */
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

    /** 付款码 */
    @XmlField("auth_code")
    private String authCode;

    /** 电子发票入口开放标识 */
    private String receipt;

    /** 场景信息 */
    @XmlField("scene_info")
    private String sceneInfo;

    @XmlField("profit_sharing")
    private String profitSharing;

    @Override
    public WxPayBaseRequest convertRequest(SupayBaseRequest request) {
        SupayPayRequest payRequest = (SupayPayRequest) request;
        this.setBody(payRequest.getTradeName());
        this.setOutTradeNo(payRequest.getTradeNo());
        this.setNotifyUrl(payRequest.getNotifyUrl());
        this.setTotalFee(payRequest.getAmount().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_DOWN).toString());
        this.setTimeStart(DateUtil.format(new Date(), "yyyyMMddHHmmss"));
        this.setTimeExpire(DateUtil.format(DateUtil.offsetMinute(new Date(), 15), "yyyyMMddHHmmss"));
        this.setTradeType(payRequest.getPayType().getCode());
        this.setSpbillCreateIp(NetUtil.getLocalhostStr());
        this.setNonceStr(String.valueOf(System.currentTimeMillis()));

        // 支付参数
        this.fillPayParam(payRequest.getPayType(), payRequest.getPayParam());

        return this;
    }

    /**
     * 填充支付参数
     * @param payType
     * @param payParam
     */
    private void fillPayParam(SupayPayType payType, SupayPayParam payParam) {
        switch (payType) {
            // h5支付场景信息
            case WX_H5_PAY:
                this.setSceneInfo(((SupayPayParamWxH5)payParam).getSceneInfo());
                break;
            case WX_MP_PAY:
                this.setAppid(((SupayPayParamWxMp)payParam).getAppId());
                this.setOpenid(((SupayPayParamWxMp)payParam).getOpenId());
                break;
            case WX_APP_PAY:
                this.setAppid(((SupayPayParamWxApp)payParam).getAppId());
                break;
            case WX_SCAN_PAY:
                break;
            case WX_FACE_PAY:
                break;
            case WX_MICRO_PAY:
                this.setAuthCode(((SupayPayParamWxMicro)payParam).getAuthCode());
                // 刷卡支付无此参数，需清空
                this.setTradeType(null);
                break;
        }
    }
}