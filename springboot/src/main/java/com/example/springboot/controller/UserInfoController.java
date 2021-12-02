package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController()
@RequestMapping("/userinfo")
public class UserInfoController {

    @Autowired
    UserInfoService userInfoService;

    @GetMapping
    public UserInfo selectAll(@RequestParam("userid") String userid){
        return userInfoService.selectAll(userid);
    }

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String uploadUrl = userInfoService.upload(file);
        return Result.success(uploadUrl);
    }

    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        userInfoService.getFiles(flag, response);
    }

    @PutMapping
    public Result update(@RequestBody UserInfo userinfo){
        return userInfoService.update(userinfo);
    }

    @DeleteMapping("/{userid}")
    public Result<?> update(@PathVariable String userid){
        return userInfoService.deleteUser(userid);
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        Page<UserInfo> page = userInfoService.findPage(pageNum, pageSize, search);
        return Result.success(page);
    }

    @DeleteMapping("/deleteIds")
    public Result deleteIds(@RequestParam(value = "selection") String[] selection){
        return userInfoService.deleteIds(selection);
    }
}
