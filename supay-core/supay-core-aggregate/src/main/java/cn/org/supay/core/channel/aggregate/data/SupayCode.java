/*******************************************************************************
 * @(#)SupayCode.java 2020年09月8日 10:40
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

import lombok.Data;

import java.io.Serializable;

/**
 * <b>Application name：</b> SupayCode.java <br>
 * <b>Application describing： </b> 定义所有支付异常编码，基于Class,可继承扩展 <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年09月8日 10:40 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
public class SupayCode implements Serializable {
    public static SupayCode SUCCESS = new SupayCode(0, "成功");
    public static SupayCode FAIL = new SupayCode(500, "失败");

    public static SupayCode UNKNOWN_EXCEPTION = new SupayCode(500, "服务器发生内部异常");
    public static SupayCode PARAMETER_FORMAT_ERROR = new SupayCode(405, "参数格式不符合要求");
    public static SupayCode PARAMETER_REQUIRED = new SupayCode(406, "缺少请求参数");
    public static SupayCode SIGN_ERROR = new SupayCode(603, "验证码无效");

    /** 编码 */
    private int code;
    /** 消息 */
    private String msg;

    SupayCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}