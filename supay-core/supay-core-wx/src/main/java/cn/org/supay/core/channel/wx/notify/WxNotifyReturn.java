/*******************************************************************************
 * @(#)WxPayNotifyData.java 2020年06月06日 22:37
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.notify;


import cn.hutool.core.util.XmlUtil;
import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.utils.BeanUtils;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <b>Application name：</b> WxPayNotifyData.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年06月06日 22:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@AllArgsConstructor
public class WxNotifyReturn {
	@XmlField("return_code")
	private String returnCode;
	@XmlField("return_msg")
	private String returnMsg;

	/**
	 * 处理成功
	 * @return
	 */
	public static String success() {
		WxNotifyReturn successRet = new WxNotifyReturn("SUCCESS", "成功");
		return XmlUtil.mapToXmlStr(BeanUtils.xmlBean2Map(successRet), false);
	}

	/**
	 * 处理失败
	 * @return
	 */
	public static String fail() {
		WxNotifyReturn successRet = new WxNotifyReturn("FAIL", "失败");
		return XmlUtil.mapToXmlStr(BeanUtils.xmlBean2Map(successRet), false);
	}

	/**
	 * 处理失败
	 * @param msg
	 * @return
	 */
	public static String fail(String msg) {
		WxNotifyReturn successRet = new WxNotifyReturn("FAIL", msg);
		return XmlUtil.mapToXmlStr(BeanUtils.xmlBean2Map(successRet), false);
	}
}