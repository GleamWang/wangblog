package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.Article;
import com.example.springboot.entity.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    Result<User> login(User user);

    Result checkToken(HttpServletRequest request);

    Result checkPerms(HttpServletRequest request);

    Page<User> findPage(Integer pageNum, Integer pageSize, String search);

    User selectUserid(String userid);

    Result insert(User user);

    Result update(User user);

    Result<User> deleteUser(String id);

    Result deleteIds(String[] selection);
}
