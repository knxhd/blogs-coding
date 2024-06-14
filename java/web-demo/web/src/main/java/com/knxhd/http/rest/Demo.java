package com.knxhd.http.rest;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.knxhd.sse.client.RequestParam;
import com.knxhd.sse.client.Result;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tianluhua
 * @version 1.0
 * @since 2024/5/21 10:12
 */
public class Demo {

    public static void main(String[] args) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory(); // 时间函数
        requestFactory.setConnectTimeout(2 * 60 * 1000);
        requestFactory.setReadTimeout(2 * 60 * 1000);
        //内部实际实现为 HttpClient
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        // 设置编码集
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();

        // 设置StringHttpMessageConverter为UTF-8
        messageConverters.removeIf(httpMessageConverter -> httpMessageConverter instanceof StringHttpMessageConverter);
        messageConverters.add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.DisableCircularReferenceDetect);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        messageConverters.add(fastJsonHttpMessageConverter);
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler());


        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("content-type", "application/json; charset=utf-8");
        headerMap.put("Accept", "application/json; charset=utf-8");

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setAll(headerMap);
        RequestParam requestParam = RequestParam.builder()
                .userId("tianluhua")
                .source("CHAT_GPT")
                .query("你好")
                .build();

        HttpEntity<Object> requestEntity = new HttpEntity<>(requestParam, requestHeaders);
        Result result = restTemplate.postForObject("http://127.0.0.1:8087/llm/invokeLlm", requestEntity, Result.class);
        if (result.getCode() == 200) {
            System.out.println("大模型返回结果：" + result.getData());
        }
    }
}
