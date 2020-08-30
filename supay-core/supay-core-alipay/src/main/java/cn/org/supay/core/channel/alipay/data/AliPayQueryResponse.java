/*******************************************************************************
 * @(#)AliPayRefundRequest.java 2020年05月29日 12:26
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.data;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.org.supay.core.channel.aggregate.data.AggregateResponseConvert;
import cn.org.supay.core.channel.aggregate.data.SupayBaseResponse;
import cn.org.supay.core.channel.aggregate.data.SupayPagePayResponse;
import cn.org.supay.core.channel.aggregate.data.SupayPayQueryResponse;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayPayStatus;
import cn.org.supay.core.enums.SupayRefundStatus;
import com.alipay.easysdk.payment.common.models.AlipayTradeQueryResponse;
import com.aliyun.tea.TeaModel;
import lombok.Data;

/**
 * <b>Application name：</b> AliPayRefundResponse.java <br>
 * <b>Application describing： </b>
 * https://opendocs.alipay.com/apis/api_1/alipay.trade.query
 * <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class AliPayQueryResponse extends AlipayTradeQueryResponse implements Response, AggregateResponseConvert {

    public static AliPayQueryResponse build(java.util.Map<String, ?> map) throws Exception {
        AliPayQueryResponse self = new AliPayQueryResponse();
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
        return SupayPayQueryResponse.builder()
                .originTradeNo(this.outTradeNo)
                .serviceTradeNo(this.tradeNo)
                .payStatus(convertPayStatus())
                .payTime(StrUtil.isNotBlank(this.sendPayDate)?DateUtil.parseTime(this.sendPayDate):null)
                .build();
    }

    /**
     * 支付状态转换
     * 交易状态：WAIT_BUYER_PAY（交易创建，等待买家付款）、TRADE_CLOSED（未付款交易超时关闭，或支付完成后全额退款）、TRADE_SUCCESS（交易支付成功）、
     * TRADE_FINISHED（交易结束，不可退款）
     * @return
     */
    private SupayPayStatus convertPayStatus() {
        if (StrUtil.isNotBlank(this.tradeStatus)) {
            switch (this.tradeStatus) {
                case "WAIT_BUYER_PAY":
                    return SupayPayStatus.NO_PAY;
                case "TRADE_FINISHED":
                case "TRADE_SUCCESS":
                    return SupayPayStatus.PAY_SUCCESS;
                case "TRADE_CLOSED":
                    return SupayPayStatus.PAY_FAIL;
            }
        }
        return null;
    }
}