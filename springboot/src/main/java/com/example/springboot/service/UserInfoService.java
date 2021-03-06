package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface UserInfoService {

    UserInfo selectAll(String userid);

    String upload(MultipartFile file) throws IOException;

    void getFiles(String flag, HttpServletResponse response);

    Result update(UserInfo userInfo);

    Page<UserInfo> findPage(Integer pageNum, Integer pageSize, String search);

    Result<UserInfo> deleteUser(String id);

    Result deleteIds(String[] selection);

}
