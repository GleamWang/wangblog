package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.Animation;
import com.example.springboot.service.AnimationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/animation")
public class AnimationController {

    @Autowired
    AnimationService animationService;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        Page<Animation> page = animationService.findPage(pageNum, pageSize, search);
        return Result.success(page);
    }

    @DeleteMapping("/{id}")
    public Result update(@PathVariable Long id) {
        return animationService.deleted(id);
    }

    @PutMapping
    public Result update(@RequestBody Animation animation) {
        return animationService.update(animation);
    }

    @PostMapping
    public Result insert(@RequestBody Animation animation) {
        animationService.insert(animation);
        return Result.success();
    }

    @DeleteMapping("/deleteIds")
    public Result deleteIds(@RequestParam(value = "selection") String[] selection) {
        return animationService.deleteIds(selection);
    }
}
