package cn.org.supay.core.channel.wx.data;

import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.aggregate.data.AggregateRequestConvert;
import cn.org.supay.core.channel.aggregate.data.SupayBaseRequest;
import cn.org.supay.core.channel.aggregate.data.SupayRefundRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * <b>Application name：</b> WxPayRefundRequest.java <br>
 * <b>Application describing： </b>
 * 微信支付-申请退款请求参数
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
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
public class WxPayRefundRequest extends WxPayBaseRequest implements AggregateRequestConvert {
  /**
   * 终端设备号
   */
  @XmlField("device_info")
  private String deviceInfo;

  /**
   * 微信生成的订单号，在支付通知中有返回
   */
  @XmlField("transaction_id")
  private String transactionId;

  /**
   * 商户侧传给微信的订单号
   */
  @XmlField("out_trade_no")
  private String outTradeNo;

  /**
   * 商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
   */
  @XmlField("out_refund_no")
  private String outRefundNo;

  /**
   * 订单总金额，单位为分，只能为整数，详见支付金额
   */
  @XmlField("total_fee")
  private Integer totalFee;

  /**
   * 退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
   */
  @XmlField("refund_fee")
  private Integer refundFee;

  /**
   * 货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
   */
  @XmlField("refund_fee_type")
  private String refundFeeType;

  /**
   * 操作员帐号, 默认为商户号
   */
  @XmlField("op_user_id")
  private String opUserId;

  /**
   * 退款资金来源
   */
  @XmlField("refund_account")
  private String refundAccount;

  /**
   * 退款资金来源
   */
  @XmlField("notify_url")
  private String notifyUrl;


  @Override
  public WxPayRefundRequest convertRequest(SupayBaseRequest request) {
    SupayRefundRequest refundRequest = (SupayRefundRequest) request;
    this.setOutTradeNo(refundRequest.getOriginTradeNo());
    this.setOutRefundNo(refundRequest.getRefundTradeNo());
    this.setRefundFee(refundRequest.getRefundAmount().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_DOWN).intValue());
    this.setTotalFee(refundRequest.getTotalAmount().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_DOWN).intValue());
    this.setNonceStr(String.valueOf(System.currentTimeMillis()));
    this.setNotifyUrl(refundRequest.getNotifyUrl());
    return this;
  }
}
