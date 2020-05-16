/*******************************************************************************
 * @(#)SpayChannelConfig.java 2020年05月15日 23:05
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.config;

import lombok.Builder;
import lombok.Data;

/**
 * <b>Application name：</b> SpayChannelConfig.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月15日 23:05 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Builder
@Data
public class SpayChannelConfig {
    /** 渠道类型 */
    private String channelType;
    /** 第三方渠道appappId */
    private String appId;
    /** 第三方渠道app秘钥 */
    private String appSecret;
    /** 第三方渠道app名称 */
    private String appName;
    /** 第三方渠道商户id */
    private String mchId;
    /** 第三方渠道商户名称 */
    private String mchName;
    /** 第三方渠道商户秘钥 */
    private String mchSecretKey;
    /** 第三方渠道证书路径 */
    private String certFile;
    /** 合作方证书密码 */
    private String certPassword;
    /** 合作方证书格式 */
    private String certFormat;
    /** 合作方基本接口地址 */
    private String apiBaseUrl;
}