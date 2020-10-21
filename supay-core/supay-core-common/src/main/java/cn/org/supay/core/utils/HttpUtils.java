/*******************************************************************************
 * @(#)HttpUtils.java 2020年05月16日 20:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

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

    /** 配置证书构建sslFactory*/
    private SSLSocketFactory sslSocketFactory = null;
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

    /**
     * 初始化ssl
     * @param mchCertFormat
     * @param mchCertFile
     * @param mchCertPassword
     * @return
     */
    public static SSLSocketFactory createSSLSocketFactory(String mchCertFormat, String mchCertFile, String mchCertPassword)  {
        if (StrUtil.hasBlank(mchCertFormat, mchCertFile)) {
            return null;
        }

        try {
            SSLSocketFactory sslSocketFactory = null;
            // 客户端证书
            KeyStore keyStore = KeyStore.getInstance(mchCertFormat);
            //加载证书
            InputStream ksIn = new FileInputStream(mchCertFile);
            keyStore.load(ksIn, mchCertPassword.toCharArray());
            ksIn.close();

            //读取证书
            KeyStore trustStore = KeyStore.getInstance(mchCertFormat);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            keyManagerFactory.init(keyStore, mchCertPassword.toCharArray());
            trustManagerFactory.init(trustStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            // 初始化SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            keyManagerFactory.init(keyStore, mchCertPassword.toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();
            sslContext.init(keyManagers, null, null);
            sslSocketFactory = sslContext.getSocketFactory();
            return sslSocketFactory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}