/*******************************************************************************
 * @(#)AliPayPageRequest.java 2020年05月29日 12:26
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.data;

import cn.org.supay.core.channel.aggregate.context.AggregateContext;
import cn.org.supay.core.channel.aggregate.data.*;
import cn.org.supay.core.context.SupayContext;
import lombok.Data;
import lombok.ToString;

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
@ToString
public class AliPayPageResponse extends AlipayBaseResponse implements AggregateResponseConvert {

    @Override
    public SupayBaseResponse convertResponse(SupayContext context) {
        AggregateContext aggregateContext = (AggregateContext) context;
        if (aggregateContext.getFinalRequest() instanceof SupayPagePayRequest) {
            return SupayPagePayResponse.builder()
                    .redirectBody(this.getBody())
                    .redirectType(RedirectType.PAGE_BODY)
                    .success(this.success)
                    .build();
        }
        if (aggregateContext.getFinalRequest() instanceof SupayH5PayRequest) {
            return SupayH5PayResponse.builder()
                    .redirectBody(this.getBody())
                    .redirectType(RedirectType.PAGE_BODY)
                    .success(this.success)
                    .build();
        }
        return null;
    }
}