/*******************************************************************************
 * @(#)Factory.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.sdk;

import com.alipay.easysdk.kernel.AlipayConstants;
import com.alipay.easysdk.kernel.Client;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.kernel.Context;
import com.alipay.easysdk.kms.aliyun.AliyunKMSClient;
import com.alipay.easysdk.kms.aliyun.AliyunKMSSigner;
import com.aliyun.tea.TeaModel;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> Factory.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月25日 19:07 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Data
public class ClientInfo {

    private String appId;
    private Context context;
    private Map<String, Client> clientHashMap = new HashMap<>();

    /**
     * 设置appid
     * @param appId
     */
    public void setAppId(String appId) {
        this.appId = appId;
    }

    /**
     * 注册客户端
     * @param clientName
     * @param client
     */
    public void registerClient(String clientName, Client client) {
        clientHashMap.put(clientName, client);
    }

    /**
     * 获取客户端
     * @param clientName
     * @return
     */
    public Client getClient(String clientName) {
        return clientHashMap.get(clientName);
    }
}