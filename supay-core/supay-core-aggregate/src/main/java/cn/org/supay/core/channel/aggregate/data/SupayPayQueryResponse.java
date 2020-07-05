/*******************************************************************************
 * @(#)SupayPayQueryResponse.java 2020年05月29日 12:26
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.channel.data.Response;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <b>Application name：</b> SupayPayQueryResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:26 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class SupayPayQueryResponse extends SupayBaseResponse implements Response {

    public String tradeNo;

    public String outTradeNo;

    public String tradeStatus;
}