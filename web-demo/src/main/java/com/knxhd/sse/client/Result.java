package com.knxhd.sse.client;

import lombok.Data;

/**
 * @author tianluhua
 * @Classname Result
 * @Date 2024/1/21 09:55
 */
@Data
public class Result<T> {

    private String message;

    private int code;

    private T data;


    /**
     * 异常
     *
     * @param code    编码
     * @param message 错误信息
     * @return {@link Result}<{@link String}>
     */
    public static Result<String> error(int code, String message) {
        Result<String> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }


    /**
     * 成功
     *
     * @param data
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setData(data);
        return result;
    }


    /**
     * 成功
     *
     * @return {@link Result}<{@link T}>
     */
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(200);
        return result;
    }
}
