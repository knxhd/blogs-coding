package com.knxhd.sse.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.asynchttpclient.AsyncHandler;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.HttpResponseStatus;
import org.asynchttpclient.Response;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * 消息处理类
 *
 * @author tianluhua
 * @version 1.0
 * @since 2024/4/28 15:34
 */
public class DefaultMessageHandler implements AsyncHandler<Response> {

    @Override
    public State onStatusReceived(HttpResponseStatus httpResponseStatus) {
        return null;
    }


    @Override
    public State onHeadersReceived(HttpHeaders httpHeaders) {
        return null;
    }

    @Override
    public State onBodyPartReceived(HttpResponseBodyPart httpResponseBodyPart) {
        byte[] bodyPartBytes = httpResponseBodyPart.getBodyPartBytes();
        String messageBody = new String(bodyPartBytes, StandardCharsets.UTF_8);
        if (messageBody.trim().isEmpty()) {
            // 跳过空行数据
            return null;
        }
        // 剔除数据行的行开头"data: "
        String data = messageBody.replaceFirst("^data:", "");
        System.out.println(data);
        if (data.length() > 0) {
            try {
                Result result = JSONObject.parseObject(data, Result.class);
                if (result.getCode() == 200) {
//                    System.out.println(String.format("大模型接收到消息：%s", result.getData()));
                } else if (result.getCode() == -200) {
//                    System.out.println(String.format("大模型接收消息结束：%s", result.getData()));
                }
            } catch (Exception ignored) {

            }
        }

        return State.CONTINUE;
    }

    @Override
    public void onThrowable(Throwable throwable) {

    }

    @Override
    public Response onCompleted() {
        return null;
    }

}
