package com.example.springboot.service;

import com.example.springboot.conf.Result;
import com.example.springboot.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    Result<User> login(User user);

    Result checkToken(HttpServletRequest request);

    User selectUserid(String userid);

    Result insert(User user);

}
