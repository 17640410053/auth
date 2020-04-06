package com.banana.auth.util;

import com.banana.auth.exception.LoginException;
import com.banana.auth.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    public RedisTemplate<String, String> stringRedisTemplate;

    private static RedisTemplate<String, String> redis;

    @PostConstruct
    public void init() {
        redis = stringRedisTemplate;
    }


    public static void set(String key, String value, long time, TimeUnit timeUnit) {
        redis.opsForValue().set(key, value, time, timeUnit);
    }

    public static String get(String key) {
        return redis.opsForValue().get(key);
    }

    public static boolean setToken(String token, User userInfo) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            redis.opsForValue().set(token, mapper.writeValueAsString(userInfo), 30, TimeUnit.MINUTES);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    public static User getUserInfo(HttpServletRequest request) {
        try {
            // 获取请求头的token，token需和前台封装的请求头token名一致
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                throw new LoginException("登录异常");
            }
            ObjectMapper mapper = new ObjectMapper();
            String json = redis.opsForValue().get(token);
            if (StringUtils.isEmpty(json)) {
                throw new LoginException("登录已过期");
            }
            return mapper.readValue(json, User.class);
        } catch (Exception e) {
            throw new LoginException("登录异常");
        }
    }

    public static void clearToken(String token) {
        redis.delete(token);
    }
}
