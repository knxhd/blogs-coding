package com.knxhd.sse.client;

import com.sun.org.apache.bcel.internal.generic.FLOAD;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 请求参数
 *
 * @author tianluhua
 * @version 1.0
 * @since 2024/5/13 9:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestParam {

    /**
     * 模型源，自研、微软、讯飞等
     */
    private String source;

    /**
     * 大模型调用内容
     */
    private String query;

    /**
     * 用户ID，用于上下文对话状态管理
     */
    private String userId;

    /**
     * 场景，例如：备忘录，AI绘画等
     * <p>
     * 根据不同场景获取不同的提示词
     */
    private String scene = "common";

    /**
     * 应用，和scene结合使用，例如：AI绘本(应用)下的生成故事脚本(场景)
     */
    private String application = "common";


    /**
     * 是否流式接口
     */
    private boolean stream = false;

    /**
     * 是否使用上下文
     */
    private boolean context = false;

    /**
     * 上下文x轮数据，最多10轮，默认35轮
     * 可为空
     */
    private int limit = 5;

    /**
     * 额外的参数，可用于填充提示词中的占位符
     * 非必须
     */
    private Map<String, String> extraParam;

    /**
     * 额外的请求头部数据
     */
    private Map<String, String> extraHeader;


    /**
     * 最大的token数
     */
    private Integer max_tokens;

    /**
     * 温度
     */
    private FLOAD temperature;

    /**
     * 频率惩罚
     */
    private Integer frequency_penalty;

    /**
     * 存在惩罚
     */
    private Integer presence_penalty;


    private String sequence;


    /**
     * 前top
     */
    private FLOAD top_p;
}
