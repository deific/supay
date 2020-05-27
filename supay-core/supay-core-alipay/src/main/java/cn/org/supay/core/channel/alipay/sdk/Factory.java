/*******************************************************************************
 * @(#)Factory.java 2020年05月25日 19:07
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.alipay.sdk;

import cn.org.supay.core.channel.BasePayChannelService;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.data.Request;
import cn.org.supay.core.data.Response;
import com.alipay.easysdk.kernel.BaseClient;
import com.alipay.easysdk.payment.common.models.AlipayTradeCreateResponse;
import com.google.common.base.Preconditions;
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
public class Factory  {
    private static final Map<String, ClientInfo> clients = new HashMap<>();

    /**
     * 设置客户端参数，只需设置一次，即可反复使用各种场景下的API Client
     *
     * @param options 客户端参数对象
     */
    public static void setOptions(BaseClient.Config options) {
        try {
            registerClient(options.appId, new com.alipay.easysdk.base.image.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.base.video.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.base.oauth.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.base.qrcode.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.marketing.openlife.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.marketing.pass.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.marketing.templatemessage.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.member.identification.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.payment.common.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.payment.huabei.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.payment.facetoface.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.payment.page.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.payment.wap.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.payment.app.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.security.textrisk.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.util.generic.Client(options));
            registerClient(options.appId, new com.alipay.easysdk.util.aes.Client(options));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void registerClient(String appId, BaseClient client) {
        ClientInfo clientInfo = clients.get(appId);
        if (clientInfo == null) {
            clientInfo = new ClientInfo();
            clientInfo.setAppId(appId);
        }
        clientInfo.registerClient(client.getClass().getCanonicalName(), client);
    }

    @SuppressWarnings("unchecked")
    private static <T> T getClient(String appId, Class<T> clazz) {
        BaseClient baseClient = clients.get(appId).getClient(clazz.getCanonicalName());
        Preconditions.checkNotNull(baseClient, "尚未注册" + clazz.getCanonicalName() + "，请先调用Factory.setOptions方法。");
        return (T) baseClient;
    }

    /**
     * 支付能力相关
     */
    public static class Payment {
        /**
         * 获取支付通用API Client
         *
         * @return 支付通用API Client
         */
        public static com.alipay.easysdk.payment.common.Client Common(String appId) {
            return getClient(appId, com.alipay.easysdk.payment.common.Client.class);
        }

        /**
         * 获取花呗相关API Client
         *
         * @return 花呗相关API Client
         */
        public static com.alipay.easysdk.payment.huabei.Client Huabei(String appId) {
            return getClient(appId, com.alipay.easysdk.payment.huabei.Client.class);
        }

        /**
         * 获取当面付相关API Client
         *
         * @return 当面付相关API Client
         */
        public static com.alipay.easysdk.payment.facetoface.Client FaceToFace(String appId) {
            return getClient(appId, com.alipay.easysdk.payment.facetoface.Client.class);
        }

        /**
         * 获取电脑网站支付相关API Client
         *
         * @return 电脑网站支付相关API Client
         */
        public static com.alipay.easysdk.payment.page.Client Page(String appId) {
            return getClient(appId, com.alipay.easysdk.payment.page.Client.class);
        }

        /**
         * 获取手机网站支付相关API Client
         *
         * @return 手机网站支付相关API Client
         */
        public static com.alipay.easysdk.payment.wap.Client Wap(String appId) {
            return getClient(appId, com.alipay.easysdk.payment.wap.Client.class);
        }

        /**
         * 获取手机APP支付相关API Client
         *
         * @return 手机APP支付相关API Client
         */
        public static com.alipay.easysdk.payment.app.Client App(String appId) {
            return getClient(appId, com.alipay.easysdk.payment.app.Client.class);
        }
    }

    /**
     * 基础能力相关
     */
    public static class Base {
        /**
         * 获取图片相关API Client
         *
         * @return 图片相关API Client
         */
        public static com.alipay.easysdk.base.image.Client Image(String appId) {
            return getClient(appId, com.alipay.easysdk.base.image.Client.class);
        }

        /**
         * 获取视频相关API Client
         *
         * @return 视频相关API Client
         */
        public static com.alipay.easysdk.base.video.Client Video(String appId) {
            return getClient(appId, com.alipay.easysdk.base.video.Client.class);
        }

        /**
         * 获取OAuth认证相关API Client
         *
         * @return OAuth认证相关API Client
         */
        public static com.alipay.easysdk.base.oauth.Client OAuth(String appId) {
            return getClient(appId, com.alipay.easysdk.base.oauth.Client.class);
        }

        /**
         * 获取小程序二维码相关API Client
         *
         * @return 小程序二维码相关API Client
         */
        public static com.alipay.easysdk.base.qrcode.Client Qrcode(String appId) {
            return getClient(appId, com.alipay.easysdk.base.qrcode.Client.class);
        }
    }

    /**
     * 营销能力相关
     */
    public static class Marketing {
        /**
         * 获取生活号相关API Client
         *
         * @return 生活号相关API Client
         */
        public static com.alipay.easysdk.marketing.openlife.Client OpenLife(String appId) {
            return getClient(appId, com.alipay.easysdk.marketing.openlife.Client.class);
        }

        /**
         * 获取支付宝卡包相关API Client
         *
         * @return 支付宝卡包相关API Client
         */
        public static com.alipay.easysdk.marketing.pass.Client Pass(String appId) {
            return getClient(appId, com.alipay.easysdk.marketing.pass.Client.class);
        }

        /**
         * 获取小程序模板消息相关API Client
         *
         * @return 小程序模板消息相关API Client
         */
        public static com.alipay.easysdk.marketing.templatemessage.Client TemplateMessage(String appId) {
            return getClient(appId, com.alipay.easysdk.marketing.templatemessage.Client.class);
        }
    }

    /**
     * 会员能力相关
     */
    public static class Member {
        /**
         * 获取支付宝身份认证相关API Client
         *
         * @return 支付宝身份认证相关API Client
         */
        public static com.alipay.easysdk.member.identification.Client Identification(String appId) {
            return getClient(appId, com.alipay.easysdk.member.identification.Client.class);
        }
    }

    /**
     * 安全能力相关
     */
    public static class Security {
        /**
         * 获取文本风险识别相关API Client
         *
         * @return 文本风险识别相关API Client
         */
        public static com.alipay.easysdk.security.textrisk.Client TextRisk(String appId) {
            return getClient(appId, com.alipay.easysdk.security.textrisk.Client.class);
        }
    }

    /**
     * 辅助工具
     */
    public static class Util {
        /**
         * 获取OpenAPI通用接口，可通过自行拼装参数，调用几乎所有OpenAPI
         *
         * @return OpenAPI通用接口
         */
        public static com.alipay.easysdk.util.generic.Client Generic(String appId) {
            return getClient(appId, com.alipay.easysdk.util.generic.Client.class);
        }

        /**
         * 获取AES128加解密相关API Client，常用于会员手机号的解密
         *
         * @return AES128加解密相关API Client
         */
        public static com.alipay.easysdk.util.aes.Client AES(String appId) {
            return getClient(appId, com.alipay.easysdk.util.aes.Client.class);
        }
    }

    /**
     * 客户端定义
     */
    public static class ClientInfo {
        private String appId;
        private Map<String, BaseClient> clientHashMap = new HashMap<>();

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
        public void registerClient(String clientName, BaseClient client) {
            clientHashMap.put(clientName, client);
        }

        /**
         * 获取客户端
         * @param clientName
         * @return
         */
        public BaseClient getClient(String clientName) {
            return clientHashMap.get(clientName);
        }
    }
}