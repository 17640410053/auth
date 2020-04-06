package com.banana.auth.mapper;

import com.banana.auth.model.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {

}
