/*******************************************************************************
 * @(#)AlipayBaseResponse.java 2020年05月29日 12:28
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.data;

import cn.org.supay.core.channel.data.Response;
import lombok.Data;
import lombok.ToString;

/**
 * <b>Application name：</b> AlipayBaseResponse.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月29日 12:28 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@ToString
public class AlipayBaseResponse implements Response {

    public boolean success;

    public int code;

    public String msg;

    protected String body;
}