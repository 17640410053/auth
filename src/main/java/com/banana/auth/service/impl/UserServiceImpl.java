package com.banana.auth.service.impl;

import com.banana.auth.exception.LoginException;
import com.banana.auth.mapper.UserMapper;
import com.banana.auth.model.User;
import com.banana.auth.service.UserService;
import com.banana.auth.util.RedisUtil;
import com.banana.auth.util.SecretUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;


    @Override
    public String login(User user) {
        if ((StringUtils.isEmpty(user.getUsername()) && StringUtils.isEmpty(user.getMail()) && StringUtils.isEmpty(user.getPhone())) || StringUtils.isEmpty(user.getPassword())) {
            throw new LoginException("用户名或密码错误");
        }
        User userInfo = userMapper.selectOne(user);
        if (userInfo == null) {
            throw new LoginException("用户名或密码错误");
        } else {
            String token;
            try {
                token = SecretUtil.encryptMd5(userInfo.getUserId() + System.currentTimeMillis());
            } catch (UnsupportedEncodingException e) {
                throw new LoginException("登录异常");
            }
            if (RedisUtil.setToken(token, userInfo)) {
                return token;
            } else {
                throw new LoginException("登录异常");
            }
        }
    }
}
