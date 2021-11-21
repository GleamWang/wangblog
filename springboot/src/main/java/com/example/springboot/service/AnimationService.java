package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.Animation;

public interface AnimationService {

    Page<Animation> findPage(Integer pageNum, Integer pageSize, String search);

    Result deleted(Long id);

    Result update(Animation animation);

    void insert(Animation animation);

    Result deleteIds(String[] selection);
}
