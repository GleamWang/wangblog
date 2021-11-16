package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.entity.Animation;
import com.example.springboot.mapper.AnimationMapper;
import com.example.springboot.service.AnimationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("AnimationService")
public class AnimationServiceImpl implements AnimationService {

    @Resource
    AnimationMapper animationMapper;

    @Override
    public Page<Animation> findPage(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Animation> wrapper = Wrappers.<Animation>lambdaQuery();
        if(StringUtils.isNotBlank(search)){
            wrapper.like(Animation::getName, search);
        }
        return animationMapper.selectPage(new Page<Animation>(pageNum, pageSize), wrapper);
    }
}



