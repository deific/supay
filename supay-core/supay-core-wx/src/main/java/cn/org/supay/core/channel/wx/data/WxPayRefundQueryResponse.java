package cn.org.supay.core.channel.wx.data;

import cn.org.supay.core.annotation.XmlField;
import lombok.Data;
import lombok.ToString;

/**
 * <b>Application name：</b> WxPayRefundQueryResponse.java <br>
 * <b>Application describing： </b>
 * 微信查询退款响应对象
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_5
 * <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:47 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@ToString(callSuper = true)
public class WxPayRefundQueryResponse extends WxPayBaseResponse {
  /**
   * 终端设备号
   */
  @XmlField("device_info")
  private String deviceInfo;

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
   * 订单总金额，单位为分，只能为整数，详见支付金额
   */
  @XmlField("total_fee")
  private Integer totalFee;

  /**
   * 应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
   */
  @XmlField("settlement_total_fee")
  private Integer settlementTotalFee;

  /**
   * 订单金额货币类型，符合ISO 4217标准的三位字母代码，默认人民币：CNY，其他值列表详见货币类型
   */
  @XmlField("fee_type")
  private String feeType;

  /**
   * 现金支付金额，单位为分，只能为整数，详见支付金额
   */
  @XmlField("cash_fee")
  private Integer cashFee;

  /**
   * 退款记录数
   */
  @XmlField("refund_count")
  private Integer refundCount;


  //具体退款记录信息，退款记录信息为动态变化，多笔订单则会出现多个数据节点，无法和javabean一一对应，默认取第一个退单号
//   <refund_fee_0>1</refund_fee_0>
//   <refund_id_0><![CDATA[2008450740201411110000174436]]></refund_id_0>
//   <refund_status_0><![CDATA[PROCESSING]]></refund_status_0>
  /**
   * 商户退款单号
   */
  @XmlField("out_refund_no_0")
  private String outRefundNo0;

  /**
   * 微信退款单号
   */
  @XmlField("refund_id_0")
  private String refundId0;

  /**
   * 退款渠道
   * ORIGINAL—原路退款
   * BALANCE—退回到余额
   * OTHER_BALANCE—原账户异常退到其他余额账户
   * OTHER_BANKCARD—原银行卡异常退到其他银行卡
   */
  @XmlField("refund_channel_0")
  private String refundChannel0;

  /**
   * 申请退款金额
   */
  @XmlField("refund_fee_0")
  private String refundFee0;

  /**
   * 退款金额
   */
  @XmlField("settlement_refund_fee_0")
  private String settlementRefundFee0;

  /**
   * 代金券类型
   * CASH--充值代金券
   * NO_CASH---非充值代金券
   * 订单使用代金券时有返回（取值：CASH、NO_CASH）。$n为下标,从0开始编号，举例：coupon_type_$0
   */
  @XmlField("coupon_type_0")
  private String couponType0;

  /**
   * 总代金券退款金额
   */
  @XmlField("coupon_refund_fee_0")
  private String couponRefundFee0;

  /**
   * 退款代金券使用数量
   */
  @XmlField("coupon_refund_count_0")
  private String couponRefundCount0;

  /**
   * 退款代金券ID
   */
  @XmlField("coupon_refund_id_0_0")
  private String couponRefundId00;

  /**
   * 单个代金券退款金额
   */
  @XmlField("coupon_refund_fee_0_0")
  private String couponRefundFee00;

  /**
   * 退款状态
   */
  @XmlField("refund_status_0")
  private String refundStatus0;

  /**
   * 退款资金来源
   */
  @XmlField("refund_account_0")
  private String refundAccount0;

  /**
   * 退款入账账户
   */
  @XmlField("refund_recv_accout_0")
  private String refundRecvAccout0;

  /**
   * 退款成功时间
   */
  @XmlField("refund_success_time_0")
  private String refundSuccessTime0;
}

