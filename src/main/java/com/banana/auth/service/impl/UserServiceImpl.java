package com.banana.auth.service.impl;

import com.banana.auth.exception.LoginException;
import com.banana.auth.mapper.UserMapper;
import com.banana.auth.model.User;
import com.banana.auth.service.UserService;
import com.banana.auth.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

@Slf4j
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
            log.info("user----->{}", userInfo.toString());
            String token = new String(DigestUtils.md5Digest((userInfo.getUserId() + System.currentTimeMillis()).getBytes()));
            log.info("token------>{}", token);
            if (RedisUtil.setToken(token, userInfo)) {
                return token;
            } else {
                throw new LoginException("登陆失败");
            }
        }
    }
}
