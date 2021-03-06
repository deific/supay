/*******************************************************************************
 * @(#)AggregateContext.java 2020年05月16日 08:49
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.channel.aggregate.context;

import cn.hutool.core.util.IdUtil;
import cn.org.supay.core.channel.aggregate.data.AggregateRequestConvert;
import cn.org.supay.core.channel.aggregate.data.AggregateResponseConvert;
import cn.org.supay.core.channel.aggregate.data.SupayBaseRequest;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.context.SupayContext;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Application name：</b> AggregateContext.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月31日 22:18 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Data
@SuperBuilder
public class AggregateContext<R extends Request, S extends Response> extends SupayContext<R, S> {
    /** 转换后最终渠道请求对象 */
    private R finalRequest;
    /** 转换后最终渠道响应对象 */
    private S finalResponse;

    /**
     * 构建上下文
     * @param channelConfig
     * @param request
     * @param isSandBox
     * @return
     */
    public static SupayContext buildContext(SupayChannelConfig channelConfig, Request request, boolean isSandBox) {
        SupayContext cxt = AggregateContext.builder()
                .tradeId(IdUtil.fastUUID())
                .channelConfig(channelConfig)
                .payType(request.getPayType())
                .isSandBox(isSandBox)
                .request(request)
                .finalRequest(request)
                .isLocalMock(false)
                .success(true)
                .build();
        return cxt;
    }

    /**
     * 交换请求
     */
    public void switchRequest() {
        R tempR = finalRequest;
        this.finalRequest = this.request;
        this.request = tempR;
    }

    /**
     * 交换响应
     */
    public void switchResponse() {
        S tempS = this.response;
        this.response = this.finalResponse;
        this.finalResponse = tempS;
    }

    /**
     * 校验请求上下文的请求响应参数类型与指定类型是否一致
     * @param r
     * @param s
     * @return
     */
    @Override
    public <T> T checkAndConvertType(Class<? extends Request> r, Class<? extends Response> s) {
        return (T) this;
    }

    /**
     *
     * @param r
     * @return
     */
    @Override
    public <Re> Re getRequest(Class<Re> r) {
        if (this.getRequest().getClass().isAssignableFrom(r)) {
            return (Re) this.getRequest();
        }
        // 尝试转换类型
        toChannelRequest(r);

        return (Re)super.getRequest();
    }

    /**
     * 转换为渠道侧参数对象
     * @param r
     */
    private void toChannelRequest(Class r) {
        // 判断是否目标类型是否实现了聚合参数转换接口，如果实现了调用转换
        if (AggregateRequestConvert.class.isAssignableFrom(r)) {
            try {
                log.debug("[转换]转换聚合请求类型 -> 渠道请求参数类型：[{}] -> [{}]", this.getRequest().getClass().getName(), r.getName());
                AggregateRequestConvert targetRequest = (AggregateRequestConvert) r.newInstance();
                this.request = targetRequest.convertRequest((SupayBaseRequest) this.getRequest());

            } catch (Exception e) {
                log.error("[转换]转换聚合参数类型异常", e);
            }
        }
    }

    /**
     * 设置响应
     * @param rsp
     */
    @Override
    public void setResponse(Response rsp) {
        super.setResponse(rsp);
        // 转换类型
        toAggregateResponse(rsp);
    }

    /**
     * 转换为聚合响应对象
     * @param rsp
     */
    private void toAggregateResponse(Response rsp) {
        if (AggregateResponseConvert.class.isAssignableFrom(rsp.getClass())) {
            try {
                AggregateResponseConvert targetResponse = (AggregateResponseConvert) rsp;
                this.finalResponse = (S) targetResponse.convertResponse(this);
                log.debug("[转换]转换渠道响应参数类型 -> 聚合响应参数类型: [{}] -> [{}]", rsp.getClass().getName(), this.finalResponse.getClass().getName());
            } catch (Exception e) {
                log.error("[转换]转换聚合参数类型异常", e);
            }
        }
    }
}