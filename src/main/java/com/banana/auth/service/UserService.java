package com.banana.auth.service;

import com.banana.auth.model.User;
import com.banana.auth.vo.UserVo;

public interface UserService {

    String login(User user);

    String loginByCode(UserVo user);

    void sendCode(User user);
}
