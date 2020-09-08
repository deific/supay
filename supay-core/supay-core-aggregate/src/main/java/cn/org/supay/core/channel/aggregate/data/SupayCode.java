/*******************************************************************************
 * @(#)SupayCode.java 2020年09月8日 10:40
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.data;

/**
 * <b>Application name：</b> SupayCode.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年09月8日 10:40 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
public enum SupayCode {
    SUCCESS("0000", "成功")





    ;

    /** 编码 */
    private String code;
    /** 消息 */
    private String msg;


    SupayCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}