package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("animation")
@Data
public class Animation {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;
}
