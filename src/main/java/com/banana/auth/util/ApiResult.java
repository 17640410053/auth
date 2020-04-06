package com.banana.auth.util;

import com.banana.auth.enums.ApiCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> ApiResult<T> success(Integer code, String msg, T data) {
        return new ApiResult<>(code, msg, data);
    }

    public static <T> ApiResult<T> success(ApiCode apiCode, T data) {
        return success(apiCode.getCode(), apiCode.getMsg(), data);
    }

    public static <T> ApiResult<T> success(T data) {
        return success(ApiCode.SUCCESS, data);
    }

    public static <T> ApiResult<T> success() {
        return success(ApiCode.SUCCESS, null);
    }

    public static <T> ApiResult<T> success(String msg, T data) {
        return success(0, msg, data);
    }

    public static <T> ApiResult<T> success(String msg) {
        return success(0, msg, null);
    }

    public static <T> ApiResult<T> fail(Integer code, String msg) {
        return new ApiResult<>(code, msg, null);
    }

    public static <T> ApiResult<T> fail(ApiCode apiCode) {
        return fail(apiCode.getCode(), apiCode.getMsg());
    }

    public static <T> ApiResult<T> fail(String msg) {
        return fail(-1, msg);
    }
}
