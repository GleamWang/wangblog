package com.example.springboot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Animation;

public interface AnimationService {

    Page<Animation> findPage(Integer pageNum, Integer pageSize, String search);
}
