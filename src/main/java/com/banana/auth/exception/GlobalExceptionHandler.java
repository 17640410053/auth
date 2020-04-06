package com.banana.auth.exception;

import com.banana.auth.enums.ApiCode;
import com.banana.auth.util.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.net.BindException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoginException.class)
    public ApiResult<String> loginException(LoginException e){
      log.info("",e);
      return ApiResult.fail(e.getMessage());
    }

    /**
     * HTTP 错误码封装
     * 参考于 org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
     *
     * @param e 异常类型
     * @return 返回结果
     */
    @ExceptionHandler(Exception.class)
    public ApiResult<String> exception(Exception e) {
        if (e instanceof BindException
                || e instanceof HttpMessageNotReadableException
                || e instanceof MethodArgumentNotValidException
                || e instanceof MissingServletRequestPartException
                || e instanceof ServletRequestBindingException
                || e instanceof TypeMismatchException) {
            // 400
            return ApiResult.fail(ApiCode.BAD_REQUEST);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            // 405
            return ApiResult.fail(ApiCode.METHOD_NOT_ALLOWED);
        } else if (e instanceof HttpMediaTypeNotAcceptableException) {
            // 406
            return ApiResult.fail(ApiCode.NOT_ACCEPTABLE);
        } else if (e instanceof HttpMediaTypeNotSupportedException) {
            // 415
            return ApiResult.fail(ApiCode.UNSUPPORTED_MEDIA_TYPE);
        } else if (e instanceof HttpMessageNotWritableException) {
            // 500
            return ApiResult.fail(ApiCode.INTERNAL_SERVER_ERROR);
        }
        return ApiResult.fail(ApiCode.FAIL);
    }
}
