package com.banana.auth.controller;


import com.banana.auth.model.User;
import com.banana.auth.service.UserService;
import com.banana.auth.util.ApiResult;
import com.banana.auth.util.RedisUtil;
import com.banana.auth.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class UserController {

    @Resource
    private UserService userServiceImpl;
    @Value("${server.port}")
    private String port;

    /**
     * 登录
     *
     * @param user 账号（用户名、手机号或邮箱），密码
     * @return token
     */
    @RequestMapping("/login")
    public ApiResult<String> login(@RequestBody User user) {
        return ApiResult.success(userServiceImpl.login(user));
    }

    /**
     * 登录使用验证码
     *
     * @param user 账号（用户名、手机号或邮箱），密码
     * @return token
     */
    @RequestMapping("/loginByCode")
    public ApiResult<String> loginByCode(@RequestBody UserVo user) {
        return ApiResult.success(userServiceImpl.loginByCode(user));
    }

    /**
     * 发送验证码
     *
     * @param user 账号（用户名、手机号或邮箱），密码
     * @return token
     */
    @RequestMapping("/sendCode")
    public ApiResult<String> sendCode(@RequestBody User user) {
        userServiceImpl.sendCode(user);
        return ApiResult.success();
    }

    /**
     * 获得用户信息
     *
     * @param request 请求头信息
     * @return 用户信息
     */
    @RequestMapping("/getUserInfo")
    public ApiResult<User> getUserInfo(HttpServletRequest request) {
        log.info("port---->{}", port);
        return ApiResult.success(RedisUtil.getUserInfo(request));
    }
}
