/*******************************************************************************
 * @(#)SpayContext.java 2020年05月15日 22:51
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.context;

import com.spay.core.config.SpayChannelConfig;
import lombok.Builder;

/**
 * <b>Application name：</b> SpayContext.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 22:51 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Builder
public class SpayContext<T> {
    private SpayChannelConfig channelConfig;


}