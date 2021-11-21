package com.example.springboot.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@TableName("article")
@Data
public class Article {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String title;
    private String content1;
    private String content2;
    private String author;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time1;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time2;
    private String category;
    private String tag;
    private String img;
    private String original;
    @TableLogic
    private Integer deleted;
}
