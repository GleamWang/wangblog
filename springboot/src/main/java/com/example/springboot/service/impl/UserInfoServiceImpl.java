package com.example.springboot.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserInfo;
import com.example.springboot.mapper.UserInfoMapper;
import com.example.springboot.service.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("UserInfoService")
public class UserInfoServiceImpl implements UserInfoService {

    @Value("${server.port}")
    private String port;

    @Value("${server.ip}")
    private String ip;

    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public Result update(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);
        return Result.success();
    }

    @Override
    @Transactional
    public Result deleteIds(String[] selection) {
        List<String> ids = new ArrayList<>();
        for(String userid:selection){
            ids.add(userid);
        }
        if (selection.length == 0) {
            return Result.error("-1","没有选中数据");
        }else{
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.in("userid",ids);
            userInfoMapper.delete(wrapper);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result<UserInfo> deleteUser(String id) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("userid",id);
        userInfoMapper.deleteByMap(map);
        return Result.success();
    }

    @Override
    public Page<UserInfo> findPage(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<UserInfo> wrapper = Wrappers.<UserInfo>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            wrapper.like(UserInfo::getUserid, search);
        }
        return userInfoMapper.selectPage(new Page<UserInfo>(pageNum, pageSize), wrapper);
    }

    @Override
    public UserInfo selectAll(String userid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userid",userid);
        return userInfoMapper.selectOne(wrapper);
    }

    @Override
    public UserInfo selectUsername(String userid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("userid",userid);
        return userInfoMapper.selectOne(wrapper);
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        //获取源文件名称
        String originalFilename = file.getOriginalFilename();
        //定义文件唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        //定义文件存储的地址
        String rootFilePath = System.getProperty("user.dir") + "/files/avatar/" + flag + "_" + originalFilename;
        //把文件写入到上传路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        //返回请求文件下载的地址
        String uploadUrl = "http://" + ip + ":" + port + "/userinfo/" + flag;
        return uploadUrl;
    }

    @Override
    public void getFiles(String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        //寻找匹配文件的地址
        ///src/main/resources/cover/
        String basePath = System.getProperty("user.dir") + "/files/avatar/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
}
