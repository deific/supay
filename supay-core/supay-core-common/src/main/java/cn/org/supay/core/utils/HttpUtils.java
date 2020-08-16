/*******************************************************************************
 * @(#)HttpUtils.java 2020年05月16日 20:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.utils;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import javax.net.ssl.SSLSocketFactory;

/**
 * <b>Application name：</b> HttpUtils.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 20:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class HttpUtils extends HttpUtil {

    /**
     * 基于ssl
     * @param sslSocketFactory
     * @param url
     * @param data
     * @return
     */
    public static String postSsl(SSLSocketFactory sslSocketFactory, String url, String data) {
        HttpRequest http = HttpRequest.post(url);
        HttpResponse response = http.setSSLSocketFactory(sslSocketFactory).body(data).execute();
        if (response.isOk()) {
            return response.body();
        }
        return null;
    }

}