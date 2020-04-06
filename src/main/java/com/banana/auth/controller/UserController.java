package com.banana.auth.controller;


import com.banana.auth.model.User;
import com.banana.auth.service.UserService;
import com.banana.auth.util.ApiResult;
import com.banana.auth.util.RedisUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController {

    @Resource
    private UserService userServiceImpl;

    /**
     * 登录
     *
     * @param user 账号（用户名、手机号或邮箱），密码
     * @return token
     */
    @RequestMapping("/login")
    public ApiResult<String> login(@RequestBody User user, HttpServletRequest request) {
        return ApiResult.success(userServiceImpl.login(user));
    }

    /**
     * 获得用户信息
     * @param request 请求头信息
     * @return 用户信息
     */
    @RequestMapping("/getUserInfo")
    public ApiResult<User> getUserInfo(HttpServletRequest request) {
        return ApiResult.success(RedisUtil.getUserInfo(request));
    }
}