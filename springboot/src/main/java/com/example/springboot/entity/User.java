package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String userid;
    private String password;
    private String role;
    private String perms;
    private Integer status;
    private String token;

}
