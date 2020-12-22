/*******************************************************************************
 * @(#)SupayResponse.java 2020年05月15日 22:40
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.enums.SupayPayStatus;
import cn.org.supay.core.enums.SupayPayType;
import cn.org.supay.core.enums.SupayPayUserType;
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
    /** 支付渠道 */
    protected SupayChannelType channelType;
    /** 支付状态 */
    protected SupayPayStatus payStatus;

    /**
     * 返回参数跳转类型
     * 不同支付方式下返回接口参数跳转不同，总的来说分为三种情况
     * URL:直接返回可跳转的url地址，跳转到第三方页面完成支付
     * PAGE_BODY:返回的html代码片段，一般是form表单，通过form表单提交
     * JSON_BODY:返回支付参数并签名，前端支付接口需要用到这些参数，如h5，app等
     *
     */
    protected RedirectType redirectType;
    /** 跳转地址 */
    protected String redirectBody;
}