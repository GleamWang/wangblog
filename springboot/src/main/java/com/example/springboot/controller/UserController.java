package com.example.springboot.controller;

import com.example.springboot.conf.Result;
import com.example.springboot.entity.User;
import com.example.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        return userService.login(user);
    }

    @GetMapping("/checkToken")
    public Result checkToken(HttpServletRequest request){
        return userService.checkToken(request);
    }

    @GetMapping("/selectUserid")
    public User selectUserid(@RequestParam("userid") String userid){
        return userService.selectUserid(userid);
    }

    /**
     * 用于将用户信息插入到User和UserInfo数据库
     * @param user
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody User user){
        return userService.insert(user);
    }

}

