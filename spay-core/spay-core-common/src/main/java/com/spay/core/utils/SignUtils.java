/*******************************************************************************
 * @(#)SignUtils.java 2020年05月16日 15:31
 * Copyright 2020 http://codegarden.com All rights reserved.
 *******************************************************************************/
package com.spay.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import cn.hutool.crypto.SecureUtil;

import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * <b>Application name：</b> SignUtils.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 codegarden.com/ 版权所有。<br>
 * <b>Company：</b> codegarden.com/ <br>
 * <b>@Date：</b> 2020年05月16日 15:31 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class SignUtils {
    /**
     * 微信公众号支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3)
     *
     * @param xmlBean Bean需要标记有XML注解
     * @param signKey 签名Key
     * @return 签名字符串
     */
    public static String signForXml(Object xmlBean, String signKey) {
        return signForMap(BeanUtil.beanToMap(xmlBean), signKey);
    }

    /**
     * 微信公众号支付签名算法(详见:https://pay.weixin.qq.com/wiki/doc/api/tools/cash_coupon.php?chapter=4_3)
     *
     * @param params  参数信息
     * @param signKey 签名Key
     * @return 签名字符串
     */
    public static String signForMap(Map<String, Object> params, String signKey) {
        SortedMap<String, Object> sortedMap = new TreeMap<>(params);
        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            Object value = params.get(key);
            if (!Objects.isNull(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key + "=" + value + "&");
            }
        }

        toSign.append("key=" + signKey);
        String toSignStr = toSign.toString();

        return SecureUtil.md5(toSignStr).toUpperCase();
    }

    /**
     * 校验签名是否正确
     *
     * @param params  需要校验的参数Map
     * @param signKey 校验的签名Key
     * @return true - 签名校验成功，false - 签名校验失败
     * @see #checkSignForMap(Map, String)
     */
    public static boolean checkSignForMap(Map<String, Object> params, String signKey) {
        String sign = signForMap(params, signKey);
        return sign.equals(params.get("sign"));
    }
}