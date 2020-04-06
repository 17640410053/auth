package com.banana.auth.model;

import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "user")
public class User {
    private String userId;
    private String username;
    private String password;
    private String mail;
    private String phone;
    private String img;
    private Integer state;
}
