/*******************************************************************************
 * @(#)ChannelApiType.java 2020年05月16日 22:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel;

/**
 * <b>Application name：</b> ChannelApiType.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 22:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class ChannelApiType {
    /** 空地址 */
    public static ChannelApiType NONE = new ChannelApiType("空", "");

    /** 名称 */
    protected String name;
    /** url地址 */
    protected String url;
    /** 是否要求ssl证书 */
    protected boolean sslCertRequired;

    public ChannelApiType(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public ChannelApiType(String name, String url, boolean sslCertRequired) {
        this.name = name;
        this.url = url;
        this.sslCertRequired = sslCertRequired;
    }

    public String getUrl() {
        return this.url;
    }

    public boolean isSslCertRequired() {
        return sslCertRequired;
    }
}