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

    public static final String SDK_VERSION = "alipay-easysdk-java-2.0.0";

    /**
     * 设置客户端参数，只需设置一次，即可反复使用各种场景下的API Client
     *
     * @param options 客户端参数对象
     */
    public static void setOptions(Config options) {
        try {
            Context context = new Context(options, SDK_VERSION);

            if (AlipayConstants.AliyunKMS.equals(context.getConfig(AlipayConstants.SIGN_PROVIDER_CONFIG_KEY))) {
                context.setSigner(new AliyunKMSSigner(new AliyunKMSClient(TeaModel.buildMap(options))));
            }

            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setAppId(options.appId);
            clientInfo.setContext(context);
            clients.put(options.appId, clientInfo);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
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
        public static com.alipay.easysdk.payment.common.Client Common(String appId) throws Exception {
            return new com.alipay.easysdk.payment.common.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取花呗相关API Client
         *
         * @return 花呗相关API Client
         */
        public static com.alipay.easysdk.payment.huabei.Client Huabei(String appId) throws Exception {
            return new com.alipay.easysdk.payment.huabei.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取当面付相关API Client
         *
         * @return 当面付相关API Client
         */
        public static com.alipay.easysdk.payment.facetoface.Client FaceToFace(String appId) throws Exception {
            return new com.alipay.easysdk.payment.facetoface.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取电脑网站支付相关API Client
         *
         * @return 电脑网站支付相关API Client
         */
        public static com.alipay.easysdk.payment.page.Client Page(String appId) throws Exception {
            return new com.alipay.easysdk.payment.page.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取手机网站支付相关API Client
         *
         * @return 手机网站支付相关API Client
         */
        public static com.alipay.easysdk.payment.wap.Client Wap(String appId) throws Exception {
            return new com.alipay.easysdk.payment.wap.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取手机APP支付相关API Client
         *
         * @return 手机APP支付相关API Client
         */
        public static com.alipay.easysdk.payment.app.Client App(String appId) throws Exception {
            return new com.alipay.easysdk.payment.app.Client(new Client(clients.get(appId).getContext()));
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
        public static com.alipay.easysdk.base.image.Client Image(String appId) throws Exception {
            return new com.alipay.easysdk.base.image.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取视频相关API Client
         *
         * @return 视频相关API Client
         */
        public static com.alipay.easysdk.base.video.Client Video(String appId) throws Exception {
            return new com.alipay.easysdk.base.video.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取OAuth认证相关API Client
         *
         * @return OAuth认证相关API Client
         */
        public static com.alipay.easysdk.base.oauth.Client OAuth(String appId) throws Exception {
            return new com.alipay.easysdk.base.oauth.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取小程序二维码相关API Client
         *
         * @return 小程序二维码相关API Client
         */
        public static com.alipay.easysdk.base.qrcode.Client Qrcode(String appId) throws Exception {
            return new com.alipay.easysdk.base.qrcode.Client(new Client(clients.get(appId).getContext()));
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
        public static com.alipay.easysdk.marketing.openlife.Client OpenLife(String appId) throws Exception {
            return new com.alipay.easysdk.marketing.openlife.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取支付宝卡包相关API Client
         *
         * @return 支付宝卡包相关API Client
         */
        public static com.alipay.easysdk.marketing.pass.Client Pass(String appId) throws Exception {
            return new com.alipay.easysdk.marketing.pass.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取小程序模板消息相关API Client
         *
         * @return 小程序模板消息相关API Client
         */
        public static com.alipay.easysdk.marketing.templatemessage.Client TemplateMessage(String appId) throws Exception {
            return new com.alipay.easysdk.marketing.templatemessage.Client(new Client(clients.get(appId).getContext()));
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
        public static com.alipay.easysdk.member.identification.Client Identification(String appId) throws Exception {
            return new com.alipay.easysdk.member.identification.Client(new Client(clients.get(appId).getContext()));
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
        public static com.alipay.easysdk.security.textrisk.Client TextRisk(String appId) throws Exception {
            return new com.alipay.easysdk.security.textrisk.Client(new Client(clients.get(appId).getContext()));
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
        public static com.alipay.easysdk.util.generic.Client Generic(String appId) throws Exception {
            return new com.alipay.easysdk.util.generic.Client(new Client(clients.get(appId).getContext()));
        }

        /**
         * 获取AES128加解密相关API Client，常用于会员手机号的解密
         *
         * @return AES128加解密相关API Client
         */
        public static com.alipay.easysdk.util.aes.Client AES(String appId) throws Exception {
            return new com.alipay.easysdk.util.aes.Client(new Client(clients.get(appId).getContext()));
        }
    }
}