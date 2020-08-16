package cn.org.supay.core.channel.wx.data;

import cn.org.supay.core.annotation.XmlField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * <b>Application name：</b> WxPayRefundQueryRequest.java <br>
 * <b>Application describing： </b>
 * 微信查询退款接口参数对象
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
 * <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:47 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class WxPayRefundQueryRequest extends WxPayBaseRequest {
  /**
   * 设备号
   */
  @XmlField("device_info")
  private String deviceInfo;

  /**
   * 签名类型，目前支持HMAC-SHA256和MD5，默认为MD5
   */
  @XmlField("sign_type")
  private String signType;

  //************以下四选一************
  /**
   * 微信订单号
   */
  @XmlField("transaction_id")
  private String transactionId;

  /**
   * 商户系统内部的订单号
   */
  @XmlField("out_trade_no")
  private String outTradeNo;

  /**
   * 商户侧传给微信的退款单号
   */
  @XmlField("out_refund_no")
  private String outRefundNo;

  /**
   * 微信生成的退款单号，在申请退款接口有返回
   */
  @XmlField("refund_id")
  private String refundId;
}
