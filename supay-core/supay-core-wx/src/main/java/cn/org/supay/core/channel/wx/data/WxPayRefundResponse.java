package cn.org.supay.core.channel.wx.data;

import cn.org.supay.core.annotation.XmlField;
import cn.org.supay.core.channel.aggregate.data.AggregateResponseConvert;
import cn.org.supay.core.channel.aggregate.data.SupayBaseResponse;
import cn.org.supay.core.channel.aggregate.data.SupayRefundResponse;
import cn.org.supay.core.context.SupayContext;
import cn.org.supay.core.enums.SupayRefundStatus;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * <b>Application name：</b> WxPayUnifiedOrderResponse.java <br>
 * <b>Application describing： </b>
 * 微信支付统一下单接口响应结果
 * https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_4
 * <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:48 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@ToString(callSuper = true)
public class WxPayRefundResponse extends WxPayBaseResponse implements AggregateResponseConvert {
  private static final long serialVersionUID = 1L;

  @XmlField("device_info")
  private String deviceInfo;

  @XmlField("transaction_id")
  private String transactionId;

  @XmlField("out_trade_no")
  private String outTradeNo;

  @XmlField("out_refund_no")
  private String outRefundNo;

  @XmlField("refund_id")
  private String refundId;

  @XmlField("refund_channel")
  private String refundChannel;

  @XmlField("refund_fee")
  private String refundFee;

  @XmlField("total_fee")
  private String totalFee;

  @XmlField("fee_type")
  private String feeType;

  @XmlField("cash_fee")
  private String cashFee;

  @XmlField("cash_refund_fee")
  private String cashRefundFee;

  @XmlField("coupon_refund_fee")
  private String couponRefundFee;

  @XmlField("coupon_refund_count")
  private Integer couponRefundCount;

  @XmlField("coupon_refund_fee_0")
  private String couponRefundFee0;

  @XmlField("coupon_refund_id_0")
  private String couponRefundId0;

  /**
   * 使用优惠券列表
   */
  private List<WxPayCoupon> couponList;

  @Override
  public SupayBaseResponse convertResponse(SupayContext context) {
    SupayRefundResponse refundResponse = SupayRefundResponse.builder().build();
    refundResponse.setOriginTradeNo(this.outTradeNo);
    refundResponse.setRefundTradeNo(this.outRefundNo);
    refundResponse.setRefundServiceId(this.refundId);
    refundResponse.setRefundFee(this.refundFee);
    refundResponse.setRefundStatus(checkResult()?SupayRefundStatus.REFUND_PROCESSING:SupayRefundStatus.REFUND_FAIL);
    refundResponse.setResultCode(checkResult()?this.getErrCode():this.getReturnCode());
    refundResponse.setResultMsg(checkResult()?this.getErrCodeDes():this.getReturnMsg());
    refundResponse.setSuccess(this.checkResult());
    return refundResponse;
  }
}
