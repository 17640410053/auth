package com.banana.auth.service.impl;

import com.banana.auth.exception.LoginException;
import com.banana.auth.mapper.UserMapper;
import com.banana.auth.model.User;
import com.banana.auth.producer.MailProducer;
import com.banana.auth.service.UserService;
import com.banana.auth.util.RedisUtil;
import com.banana.auth.util.SecretUtil;
import com.banana.auth.vo.MailVo;
import com.banana.auth.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private MailProducer producer;


    @Override
    public String login(User user) {
        if ((StringUtils.isEmpty(user.getUsername()) && StringUtils.isEmpty(user.getMail()) && StringUtils.isEmpty(user.getPhone())) || StringUtils.isEmpty(user.getPassword())) {
            throw new LoginException("用户名或密码错误");
        }
        User userInfo = userMapper.selectOne(user);
        if (userInfo == null) {
            throw new LoginException("用户名或密码错误");
        } else {
            return createToken(userInfo);
        }
    }

    @Override
    public String loginByCode(UserVo user) {
        if (StringUtils.isEmpty(user.getCode())) {
            throw new LoginException("验证码为空");
        }
        if (user.getCode().equals(RedisUtil.get(user.getMail()))) {
            User userInfo = userMapper.selectOne(user);
            if (userInfo == null) {
                throw new LoginException("账户不存在");
            } else {
                RedisUtil.clear(user.getMail());
                return createToken(userInfo);
            }
        } else {
            throw new LoginException("验证码不正确");
        }
    }

    @Override
    public void sendCode(User user) {
        if (StringUtils.isEmpty(user.getMail())) {
            throw new LoginException("邮箱不能为空...");
        }
        try {
            Random random = new Random();
            String code = Integer.toString(random.nextInt(9000) + 1000);
            producer.sendMsg(MailVo.builder()
                    .to(user.getMail())
                    .subject("验证码")
                    .text("你的验证码是：" + code + "，有效时间5分钟")
                    .build());
            RedisUtil.set(user.getMail(), code, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new LoginException("发送失败");
        }
    }

    public String createToken(User userInfo) {
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
