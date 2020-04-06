package com.banana.auth.vo;

import com.banana.auth.model.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserVo extends User {
    private String code;
}
