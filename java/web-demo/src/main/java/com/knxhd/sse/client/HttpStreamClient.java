package com.knxhd.sse.client;

import com.alibaba.fastjson.JSONObject;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.*;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * webSocket客户端，调用的是http的流式接口
 *
 * @author tianluhua
 * @version 1.0
 * @since 2024/4/28 15:33
 */
@Slf4j
@NoArgsConstructor
public class HttpStreamClient {

    private AsyncHandler<Response> asyncHandler;

    public HttpStreamClient(AsyncHandler<Response> asyncHandler) {
        this.asyncHandler = asyncHandler;
    }


    public void sendMessage(String url, String message) {
        // 组装okhttp请求
        AsyncHttpClient client = Dsl.asyncHttpClient();
        BoundRequestBuilder requestBuilder = client.preparePost(url);
        requestBuilder.setHeader("Accept", "text/event-stream");
        requestBuilder.setHeader("Content-Type", "application/json");
        requestBuilder.setBody(message);
        requestBuilder.execute(asyncHandler);
    }

    public static void main(String[] args) {
        DefaultMessageHandler defaultMessageHandler = new DefaultMessageHandler();
        HttpStreamClient httpStreamClient = new HttpStreamClient(defaultMessageHandler);
        RequestParam requestParam = RequestParam.builder()
                .userId("test_tianluhua_QWEN_VL")
                .source("QWEN_VL")
                .query("图一和图二有什么区别")
                .stream(true)
                .context(true)
                .build();
        httpStreamClient.sendMessage("http://127.0.0.1:8089/llm-platform/api/packDesignGenerateResults/testSse", JSONObject.toJSONString(requestParam));
    }
}
