/*******************************************************************************
 * @(#)SupayContext.java 2020年05月15日 22:51
 * Copyright 2020 http://supay.org.cn All rights reserved.
 *******************************************************************************/
package cn.org.supay.core.context;

import cn.hutool.core.util.IdUtil;
import cn.org.supay.core.channel.converter.ChannelDataConverter;
import cn.org.supay.core.channel.data.Request;
import cn.org.supay.core.channel.data.Response;
<<<<<<< HEAD
import cn.org.supay.core.config.SupayChannelConfig;
import cn.org.supay.core.config.SupayCoreConfig;
import cn.org.supay.core.stats.InvokeStats;
=======
import cn.org.supay.core.enums.SupayPayType;
>>>>>>> parent of b8cde8c... feat:多层级调用监控统计机制
import lombok.Data;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>Application name：</b> SupayContext.java <br>
 * <b>Application describing： </b> <br>
 * <b>Copyright：</b> Copyright &copy; 2020 supay.org.cn/ 版权所有。<br>
 * <b>Company：</b> supay.org.cn/ <br>
 * <b>@Date：</b> 2020年05月15日 22:51 <br>
 * <b>@author：</b> <a href="mailto:deific@126.com"> deific </a> <br>
 * <b>@version：</b>V1.0.0 <br>
 */
@Slf4j
@Data
@SuperBuilder
@ToString(callSuper = true)
public class SupayContext<R extends Request, S extends Response> {
    /** 交易流水号 */
    protected String tradeId;
    /** 支付渠道参数 */
    protected SupayChannelConfig channelConfig;
    /** 支付请求参数 */
    protected R request;
    /** 支付结果 */
    protected S response;
    /** 调用信息 */
    protected InvokeStats currentInvoke;
    /** 附加参数 */
    protected Map<String, Object> extra;
    /** 是否启动本地模拟支付 */
    protected boolean isLocalMock;
    /** 是否聚合支付 */
    protected boolean isAggregate;
    /** 是否使用渠道沙盒环境 */
    protected boolean isSandBox;
    /** 交易是否成功 */
    protected boolean success = true;
    /** 交易信息 */
    protected String msg;

    /**
     * 设置请求
     * @param r
     */
    public void setRequest(Request r) {
        this.request = (R) r;
    }

    /**
     * 设置响应
     * @param s
     */
    public void setResponse(Response s) {
        this.response = (S) s;
    }

    /**
     * 成功
     * @param msg
     * @return
     */
    public SupayContext success(String msg) {
        this.setMsg(msg);
        this.setSuccess(true);
        return this;
    }

    /**
     * 成功
     * @param response
     * @param msg
     * @return
     */
    public SupayContext success(S response, String msg) {
        this.response = response;
        this.setMsg(msg);
        this.setSuccess(true);
        return this;
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public SupayContext fail(String msg) {
        this.setMsg(msg);
        this.setSuccess(false);
        return this;
    }

    /**
     * 是否有错误
     * @return
     */
    public boolean hasError() {
        return !success;
    }

    /**
     * 转换为请求字符串
     * @return
     */
    public String toRequestStr() {
        ChannelDataConverter converter = SupayCoreConfig.getApiParamConverter(this.channelConfig.getChannelType());
        return converter.convert(this.getRequest());
    }

    /**
     * 解析响应字符串为对象实例
     * @param respStr
     * @param targetClass
     * @return
     */
    public Response parseResponseStr(String respStr, Class<? extends Response> targetClass) {
        ChannelDataConverter converter = SupayCoreConfig.getApiParamConverter(this.channelConfig.getChannelType());
        this.setResponse((S)converter.convert(respStr, targetClass));
        // 检查结果
        this.getResponse().checkResult(this);
        return this.response;
    }

    /**
     * 开始计时
     */
    public InvokeStats startInvoke(String invokeService, String method, InvokeStats parentInvoke) {
        if (parentInvoke == null) {
            currentInvoke = new InvokeStats(this.getChannelConfig().getChannelType(), 0, invokeService, method, new Date());
        } else {
            currentInvoke = new InvokeStats(this.getChannelConfig().getChannelType(), parentInvoke.getInvokeLevel() + 1, invokeService, method, new Date());
            parentInvoke.setNextInvoke(currentInvoke);
        }
        return currentInvoke;
    }

    /**
     * 结束调用
     */
    public InvokeStats endInvoke(String invokeService, String method, InvokeStats parentInvoke) {
        this.currentInvoke.setEndTime(new Date());
        this.currentInvoke.setInvokeCost(this.currentInvoke.getEndTime().getTime() - this.currentInvoke.getStartTime().getTime());
        this.currentInvoke = parentInvoke == null?this.currentInvoke:parentInvoke;
        return this.currentInvoke;
    }

    public int getInvokeLevel() {
        return this.currentInvoke.getInvokeLevel();
    }

    /**
     * 获取渠道调用统计
     * @return
     */
    public InvokeStats getChannelInvoke(InvokeStats invokeStats) {
        if (invokeStats.getNextInvoke() == null) {
            return invokeStats;
        } else {
            return this.getChannelInvoke(invokeStats.getNextInvoke());
        }
    }

    /**
     * 耗时
     * @return
     */
    public long duration() {
        return this.currentInvoke.getInvokeCost();
    }

    /**
     * 校验请求上下文的请求响应参数类型与指定类型是否一致
     * @param r
     * @param s
     * @return
     */
    public <T> T checkAndConvertType(Class<? extends Request> r, Class<? extends Response> s) {
        // 判断类型是否匹配
        boolean isMatch = (this.getRequest() != null && r.isInstance(this.getRequest()));
        if (!isMatch && this.getRequest() != null) {
            this.fail("请求类型与当前渠道要求类型不匹配，要求类型：" + r.getName() + " 请求类型：" + this.getRequest().getClass());
            log.error(this.msg);
            return (T)this;
        }

        isMatch = this.getResponse() != null && s.isInstance(this.getResponse());
        if (!isMatch && this.getResponse() != null) {
            this.fail("响应类型与当前渠道要求类型不匹配，要求类型：" + s.getName() + " 响应类型：" + this.getResponse().getClass());
            log.error(this.msg);
            return (T)this;
        }

        // 参数检查
        return (T)this;
    }

    /**
     *
     * @param r
     * @param <R>
     * @return
     */
    public <R> R getRequest(Class<R> r) {
        if (this.getRequest().getClass().isAssignableFrom(r)) {
            return (R) this.getRequest();
        }
        return null;
    }
    /**
     * 构建上下文
     * @param channelConfig
     * @param request
     * @param isSandBox
     * @return
     */
    public static SupayContext buildContext(SupayChannelConfig channelConfig, Request request, boolean isSandBox) {
        SupayContext cxt = SupayContext.builder()
                .tradeId(IdUtil.fastUUID())
                .channelConfig(channelConfig)
                .isSandBox(isSandBox)
                .request(request)
                .isLocalMock(false)
                .success(true)
                .build();

        return cxt;
    }

    /**
     * 添加数据
     * @param key
     * @param data
     */
    public void addData(String key, Object data) {
        if (extra == null) {
            extra = new HashMap<>();
        }
        extra.put(key, data);
    }

    /**
     * 获取数据
     * @param key
     * @param <T>
     * @return
     */
    public <T> T getData(String key) {
        return extra == null?null:(T) extra.get(key);
    }
}