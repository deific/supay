/*******************************************************************************
 * @(#)SPayData.java 2020年05月15日 22:40
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.data;

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
public class SpayResponse<T> {
    /** 业务处理编码 */
    private String resultCode;
    /** 业务处理信息 */
    private String resultMsg;
    /** 业务处理是否成功 */
    private boolean success;
    /** 跳转地址 */
    private String redirectUrl;
    /** 跳转页面内容 */
    private String redirectPageBody;
    /** 业务数据 */
    private T data;
}