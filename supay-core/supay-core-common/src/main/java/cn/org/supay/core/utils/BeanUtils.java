/*******************************************************************************
 * @(#)BeanUtils.java 2020年05月16日 16:43
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.XmlUtil;
import cn.org.supay.core.annotation.XmlField;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * <b>Application name：</b> BeanUtils.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 16:43 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public class BeanUtils extends BeanUtil {

    /**
     *
     * 将bean按照@XmlAlias标识的字符串内容生成以之为key的map对象
     * @param bean 包含@XmlAlias的xml bean对象
     * @return map对象
     */
    public static Map<String, Object> xmlBean2Map(Object bean) {
        Map<String, Object> result = new HashMap<>(16);
        List<Field> fields = new ArrayList<>(Arrays.asList(bean.getClass().getDeclaredFields()));

        Class superClass = bean.getClass().getSuperclass();
        fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
        superClass = superClass.getSuperclass();
        if (superClass != null) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
        }

        for (Field field : fields) {
            try {
                boolean isAccessible = field.isAccessible();
                field.setAccessible(true);
                if (field.get(bean) == null || field.getModifiers() == Modifier.STATIC) {
                    field.setAccessible(isAccessible);
                    continue;
                }

                if (field.isAnnotationPresent(XmlField.class)) {
                    result.put(field.getAnnotation(XmlField.class).value(), field.get(bean));
                } else {
                    result.put(field.getName(), field.get(bean));
                }

                field.setAccessible(isAccessible);
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * xml字符串转换为bean对象
     * @param xml
     * @param targetClass
     * @param <T>
     * @return
     */
    public static <T> T xmlToBean(String xml, Class<T> targetClass) {
        Map<String, Object> xmlMap = XmlUtil.xmlToMap(xml);
        return map2XmlBean(xmlMap, targetClass);
    }
    /**
     *
     * 将bean按照@XmlAlias标识的字符串内容生成以之为key的map对象
     * @param  map xml数据map对象
     * @param  targetClass 转换目标对象@XmlField
     * @return bean 包含@XmlField的xml bean对象
     */
    public static <T> T map2XmlBean(Map<String, Object> map, Class<T> targetClass) {
        Map<String, Object> result = new HashMap<>(16);

        T xmlBean = ReflectUtil.newInstance(targetClass, new Object[0]);

        List<Field> fields = new ArrayList<>(Arrays.asList(xmlBean.getClass().getDeclaredFields()));
        Class superClass = xmlBean.getClass().getSuperclass();
        fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
        superClass = superClass.getSuperclass();
        if (superClass != null) {
            fields.addAll(Arrays.asList(superClass.getDeclaredFields()));
        }

        for (Field field : fields) {
            if (map.get(field.getName()) == null) {
                continue;
            }
            if (field.isAnnotationPresent(XmlField.class)) {
                ReflectUtil.setFieldValue(xmlBean, field, map.get(field.getAnnotation(XmlField.class).value()));
            } else {
                ReflectUtil.setFieldValue(xmlBean, field, map.get(field.getName()));
            }
        }
        return xmlBean;
    }
}