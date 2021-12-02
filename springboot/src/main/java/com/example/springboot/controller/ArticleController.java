package com.example.springboot.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.Article;
import com.example.springboot.entity.MyNum;
import com.example.springboot.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController()
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping
    public Result insert(@RequestBody Article article) {
        articleService.insert(article);
        return Result.success();
    }

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String uploadUrl = articleService.upload(file);
        return Result.success(uploadUrl);
    }

    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response) {
        articleService.getFiles(flag, response);
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        Page<Article> page = articleService.findPage(pageNum, pageSize, search);
        return Result.success(page);
    }

    @GetMapping("/selectAuthor")
    public Result<?> findPageByAuthor(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam("author") String author) {
        Page<Article> page = articleService.findPageByAuthor(pageNum, pageSize, author);
        return Result.success(page);
    }

    @GetMapping("/selectId")
    public Article selectById(@RequestParam("id") String id) {
        return articleService.selectById(id);
    }

    @GetMapping("/selectTag")
    public Result<?> findPageByTag(@RequestParam(defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "10") Integer pageSize,
                                   @RequestParam("tag") String tag) {
        Page<Article> tagPage = articleService.findPageByTag(pageNum, pageSize, tag);
        return Result.success(tagPage);
    }

    @GetMapping("/selectCategory")
    public Result<?> findPageByCategory(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam("category") String category) {
        Page<Article> categoryPage = articleService.findPageByCategory(pageNum, pageSize, category);
        return Result.success(categoryPage);
    }

    @GetMapping("/selectMyNum")
    public MyNum findMyArticleNum(){
        return articleService.findMyArticleNum("Gleam");
    }

    @PutMapping
    public Result update(@RequestBody Article article){
        return articleService.update(article);
    }

    @DeleteMapping("/{id}")
    public Result update(@PathVariable Long id){
        return articleService.deleted(id);
    }

    @DeleteMapping("/deleteIds")
    public Result deleteIds(@RequestParam(value = "selection") String[] selection){
        return articleService.deleteIds(selection);
    }
}
