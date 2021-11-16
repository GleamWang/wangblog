package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("userinfo")
@Data
public class UserInfo {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userid;
    private String username;
    private String role;
    private String avatar;
}
