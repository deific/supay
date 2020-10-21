/*******************************************************************************
 * @(#)SupayChannelConfig.java 2020年05月15日 23:05
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.config;

import cn.org.supay.core.enums.KeyStoreType;
import cn.org.supay.core.enums.SupayChannelType;
import cn.org.supay.core.utils.HttpUtils;
import lombok.Builder;
import lombok.Data;

import javax.net.ssl.*;

/**
 * <b>Application name：</b> SupayChannelConfig.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 23:05 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Builder
@Data
public class SupayChannelConfig {
    /** 渠道类型 {@link SupayChannelType} */
    private SupayChannelType channelType;
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
    private String appCertFile;
    /** 合作方证书密码 */
    private String appCertPassword;
    /** 合作方证书格式 */
    private String appCertFormat;

    /** 第三方渠道证书路径 */
    private String mchCertFile;
    /** 合作方证书密码 */
    private String mchCertPassword;
    /** 合作方证书格式 */
    private KeyStoreType mchCertFormat;

    /** 根密钥 */
    private String rootSecretKey;
    /** 根证书 */
    private String rootCertFile;

    /** 合作方基本接口地址 */
    private String apiBaseUrl;
    /** 是否启用沙箱环境 */
    private boolean sandBox;

    /** 配置证书构建sslFactory*/
    private SSLSocketFactory sslSocketFactory = null;

    /**
     * 注册渠道参数配置
     */
    public SupayChannelConfig register() {
        sslSocketFactory = HttpUtils.createSSLSocketFactory(this.getMchCertFormat().name(), this.getMchCertFile(), this.getMchCertPassword());
        SupayCoreConfig.registerChannelConfig(appId, this);
        return this;
    }

}