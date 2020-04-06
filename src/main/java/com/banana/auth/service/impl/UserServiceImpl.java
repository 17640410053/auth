package com.banana.auth.service.impl;

import com.banana.auth.exception.LoginException;
import com.banana.auth.mapper.UserMapper;
import com.banana.auth.model.User;
import com.banana.auth.service.UserService;
import com.banana.auth.util.RedisUtil;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public String login(User user) {
        User userInfo = userMapper.selectOne(user);
        if (userInfo == null) {
            throw new LoginException("用户名或密码错误");
        } else {
            String token = MD5Encoder.encode((userInfo.getUserId() + System.currentTimeMillis()).getBytes());
            if (RedisUtil.setToken(token, userInfo)) {
                return token;
            } else {
                throw new LoginException("登陆失败");
            }
        }
    }
}
