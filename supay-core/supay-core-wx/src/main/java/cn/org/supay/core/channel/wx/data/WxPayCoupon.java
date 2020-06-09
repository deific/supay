/*******************************************************************************
 * @(#)WxPayCoupon.java 2017年04月18日 17:33
 * Copyright 2020 supay.org.cn. All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.wx.data;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> WxPayCoupon.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月16日 09:37 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Data
@SuperBuilder
public class WxPayCoupon extends WxPayData implements Serializable {
    private String couponId;
    private String couponType;
    private Integer couponFee;

    public Map<String, String> toMap(int index) {
        Map<String, String> map = new HashMap<>();
        map.put("coupon_id_" + index, this.getCouponId());
        map.put("coupon_type_" + index, this.getCouponType());
        map.put("coupon_fee_" + index, this.getCouponFee() + "");
        return map;
    }
}
