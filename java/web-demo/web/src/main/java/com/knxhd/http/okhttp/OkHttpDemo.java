package com.knxhd.http.okhttp;

import com.alibaba.fastjson.JSON;
import com.knxhd.sse.client.RequestParam;
import com.knxhd.sse.client.Result;

/**
 * @author tianluhua
 * @version 1.0
 * @since 2024/5/21 10:19
 */
public class OkHttpDemo {

    public static void main(String[] args) {
        String url = "http://127.0.0.1:8087/llm/invokeLlm";
        // 组装请求体参数
        RequestParam requestParam = RequestParam.builder()
                .userId("tianluhua")
                .source("CHAT_GPT")
                .query("你好")
                .build();
        // 组装okhttp请求
        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder().build();
        okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/json");
        okhttp3.RequestBody body = okhttp3.RequestBody.create(JSON.toJSONString(requestParam), mediaType);
        okhttp3.Request request = new okhttp3.Request.Builder().url(url).method("POST", body).build();
        try {
            // 执行请求
            okhttp3.Response response = client.newCall(request).execute();
            // 获取响应内容
            String respStr = java.util.Objects.requireNonNull(response.body()).string();
            // 序列化Json为Java对象
            Result result = JSON.parseObject(respStr, Result.class);
            if (result.getCode() == 200) {
                System.out.println("大模型返回结果：" + result.getData());
            }
        } catch (java.io.IOException | IndexOutOfBoundsException e) {
            // TODO 记录请求异常信息
            e.printStackTrace();
            System.out.println("");
        }
    }

}
