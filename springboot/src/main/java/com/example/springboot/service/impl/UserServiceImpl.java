package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.springboot.common.utils.JwtUtils;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.mapper.UserMapper;
import com.example.springboot.service.UserService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Random;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Resource
    UserInfoMapper userInfoMapper;

    @Autowired
    JwtUtils jwtUtils;

    @Override
    @Transactional
    public Result update(User user) {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
        updateWrapper.eq("userid",user.getUserid());
        userMapper.update(user, updateWrapper);
        return Result.success();
    }

    @Override
    @Transactional
    public Result<User> deleteUser(String id) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("userid",id);
        userMapper.deleteByMap(map);
        return Result.success();
    }

    @Override
    public Result insert(User user) {
        userMapper.insert(user);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserid(user.getUserid());

        //生成随机用户昵称
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<5;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        String random_name = "博客用户_" + sb.toString();
        userInfo.setUsername(random_name);

        //同步插入数据到userinfo数据库
        userInfoMapper.insert(userInfo);
        return Result.success();
    }

    @Override
    public User selectUserid(String userid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userid",userid);
        return userMapper.selectOne(wrapper);
    }

    @Override
    @Transactional
    public Result<User> login(User user) {
        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUserid, user.getUserid()).eq(User::getPassword, user.getPassword()));
        //账号密码错误
        if(res == null){
            return Result.error("-1","用户名或密码错误");
        }
        //账号密码正确
        else{
            res.setToken(jwtUtils.createToken(res));
            //更新数据库数据，将token插入数据库
            userMapper.updateById(res);
            return Result.success(res);
        }
    }

    @Override
    public Result checkToken(HttpServletRequest request) {
        String token = request.getHeader("token");
        Claims claims = jwtUtils.checkToken(token);
        if(claims == null){
            return Result.error("-1","token校验失败");
        }
        else
            return Result.success();
    }
}
