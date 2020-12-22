/*******************************************************************************
 * @(#)AliPayPageRequest.java 2020年05月29日 12:26
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.data;

import cn.org.supay.core.channel.aggregate.data.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;

/**
 * <b>Application name：</b> AliPayPageRequest.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class AliPayPageRequest extends AliPayBaseRequest implements AggregateRequestConvert {

    /** 支付完成返回地址 */
    String returnUrl;

    @Override
    public AliPayPageRequest convertRequest(SupayBaseRequest request) {
        if (request instanceof SupayPagePayRequest) {
            SupayPagePayRequest pagePayRequest = (SupayPagePayRequest) request;
            this.setPayType(pagePayRequest.getPayType());
            this.setOutTradeNo(pagePayRequest.getTradeNo());
            this.setSubject(pagePayRequest.getTradeName());
            this.setTotalAmount(pagePayRequest.getAmount().toString());
            this.setReturnUrl(pagePayRequest.getReturnUrl());
        }
        if (request instanceof SupayH5PayRequest) {
            SupayH5PayRequest h5PayRequest = (SupayH5PayRequest) request;
            this.setPayType(h5PayRequest.getPayType());
            this.setOutTradeNo(h5PayRequest.getTradeNo());
            this.setSubject(h5PayRequest.getTradeName());
            this.setTotalAmount(h5PayRequest.getAmount().toString());
            this.setReturnUrl(h5PayRequest.getReturnUrl());
        }
        this.setOptionParams(new HashMap<>());
        return this;
    }
}