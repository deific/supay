/*******************************************************************************
 * @(#)SPayData.java 2020年05月15日 22:40
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.channel.aggregate.data;

import com.spay.core.data.Response;
import lombok.Data;

/**
 * <b>Application name：</b> SPayData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 22:40 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class SpayResponse extends Response {
    /** 业务处理编码 */
    protected String resultCode;
    /** 业务处理信息 */
    protected String resultMsg;
    /** 业务处理是否成功 */
    protected boolean success;
    /** 跳转地址 */
    protected String redirectUrl;
    /** 跳转页面内容 */
    protected String redirectPageBody;
}