package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.Animation;
import com.example.springboot.entity.Article;
import com.example.springboot.mapper.AnimationMapper;
import com.example.springboot.service.AnimationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    @Transactional
    public Result deleted(Long id) {
        animationMapper.deleteById(id);
        return Result.success();
    }
    @Override
    @Transactional
    public Result update(Animation animation) {
        animationMapper.updateById(animation);
        return Result.success();
    }

    @Override
    @Transactional
    public void insert(Animation animation) {
        animationMapper.insert(animation);
    }

    @Override
    @Transactional
    public Result deleteIds(String[] selection) {
        List<String> ids = new ArrayList<>();
        for(String id:selection){
            ids.add(id);
        }
        if (selection.length == 0) {
            return Result.error("-1","没有选中数据");
        }else{
            QueryWrapper<Animation> wrapper = new QueryWrapper<>();
            wrapper.in("id",ids);
            animationMapper.delete(wrapper);
        }
        return Result.success();
    }
}



