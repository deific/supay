/*******************************************************************************
 * @(#)SupayResponse.java 2020年05月15日 22:40
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.enums.SupayPayType;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * <b>Application name：</b> SupayResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:40 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class SupayPayResponse extends SupayBaseResponse implements Response {
    /** 支付方式 */
    protected SupayPayType payType;
    /** 跳转地址 */
    protected String redirectUrl;
    /** 跳转页面内容 */
    protected String redirectPageBody;

}