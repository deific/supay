/*******************************************************************************
 * @(#)AliPayRefundRequest.java 2020年05月29日 12:26
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.data;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.org.supay.core.channel.aggregate.data.AggregateResponseConvert;
import cn.org.supay.core.channel.aggregate.data.SupayBaseResponse;
import cn.org.supay.core.channel.aggregate.data.SupayPayQueryResponse;
import cn.org.supay.core.channel.aggregate.data.SupayRefundQueryResponse;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayPayStatus;
import cn.org.supay.core.enums.SupayRefundStatus;
import com.alipay.easysdk.payment.common.models.AlipayTradeFastpayRefundQueryResponse;
import com.aliyun.tea.TeaModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <b>Application name：</b> AliPayRefundResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class AliPayRefundQueryResponse extends AlipayTradeFastpayRefundQueryResponse implements Response, AggregateResponseConvert {

    public static AliPayRefundQueryResponse build(java.util.Map<String, ?> map) throws Exception {
        AliPayRefundQueryResponse self = new AliPayRefundQueryResponse();
        return TeaModel.toModel(map, self);
    }

    @Override
    public String toString() {
        try {
            return JSONUtil.toJsonStr(this.toMap());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SupayBaseResponse convertResponse(SupayContext context) {
        return SupayRefundQueryResponse.builder()
                .originTradeNo(this.outTradeNo)
                .refundTradeNo(this.outRequestNo)
                .refundAmount(new BigDecimal(this.refundAmount))
                .refundStatus(convertRefundStatus())
                .build();
    }

    /**
     * 转换退款状态
     * 如果该接口返回了查询数据，且refund_status为空或为REFUND_SUCCESS，则代表退款成功，如果没有查询到则代表未退款成功，可以调用退款接口进行重试。重试时请务必保证退款请求号一致。
     * @return
     */
    private SupayRefundStatus convertRefundStatus() {
        this.refundStatus = StrUtil.nullToEmpty(this.refundStatus);
        switch (this.refundStatus) {
            case "":
            case "REFUND_SUCCESS":
                return SupayRefundStatus.REFUND_SUCCESS;
            default:
                return SupayRefundStatus.REFUND_FAIL;
        }
    }
}