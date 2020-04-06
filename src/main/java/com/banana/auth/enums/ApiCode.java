package com.banana.auth.enums;

public enum ApiCode {
    SUCCESS(0, "操作成功"),
    FAIL(-1, "未知错误"),
    LOGIN_ERROR(10000, "登录失败"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),
    NOT_ACCEPTABLE(406, "Not Acceptable"),
    UNSUPPORTED_MEDIA_TYPE(415, "Unsupported Media Type"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error")

    ;
    private Integer code;
    private String msg;

    ApiCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
