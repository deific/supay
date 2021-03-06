/*******************************************************************************
 * @(#)SupayResponse.java 2020年05月15日 22:40
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import cn.org.supay.core.channel.data.Response;
import lombok.Data;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class SupayBaseResponse implements Response {
    /** 业务处理编码 */
    protected String resultCode;
    /** 业务处理信息 */
    protected String resultMsg;
    /** 业务处理是否成功 */
    protected boolean success;
}