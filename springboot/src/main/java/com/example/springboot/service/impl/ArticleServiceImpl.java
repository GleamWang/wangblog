package com.example.springboot.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.conf.Result;
import com.example.springboot.entity.Article;
import com.example.springboot.entity.MyNum;
import com.example.springboot.mapper.ArticleMapper;
import com.example.springboot.service.ArticleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Service("ArticleService")
public class ArticleServiceImpl implements ArticleService {

    @Value("${server.port}")
    private String port;

    @Value("${server.ip}")
    private String ip;

    @Resource
    ArticleMapper articleMapper;

    @Override
    @Transactional
    public void insert(Article article) {
        article.setTime1(new Date());
        article.setTime2(new Date());
        articleMapper.insert(article);
    }

    @Override
    public Page<Article> findPage(Integer pageNum, Integer pageSize, String search) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if (StringUtils.isNotBlank(search)) {
            wrapper.like(Article::getTitle, search);
        }
        return articleMapper.selectPage(new Page<Article>(pageNum, pageSize), wrapper);
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
            QueryWrapper<Article> wrapper = new QueryWrapper<>();
            wrapper.in("id",ids);
            articleMapper.delete(wrapper);
        }
        return Result.success();
    }

    @Override
    @Transactional
    public Result deleted(Long id) {
        articleMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Article selectById(String id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("id", id);
        return articleMapper.selectOne(wrapper);
    }

    @Override
    public Page<Article> findPageByTag(Integer pageNum, Integer pageSize, String tag) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if (StringUtils.isNotBlank(tag)) {
            wrapper.like(Article::getTag, tag);
        }
        return articleMapper.selectPage(new Page<Article>(pageNum, pageSize), wrapper);
    }

    @Override
    @Transactional
    public Result update(Article article) {
        article.setTime2(new Date());
        articleMapper.updateById(article);
        return Result.success();
    }

    @Override
    public MyNum findMyArticleNum(String userid) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.like("author", userid);
        List<Article> list = articleMapper.selectList(wrapper);
        Set categorySet = new HashSet();
        Set tagSet = new HashSet();
        for (Article article : list) {
            categorySet.add(article.getCategory());
            String[] res = article.getTag().split(",");
            for (String item : res) {
                tagSet.add(item);
            }
        }
        //文章分类数量
        Integer categoryNum = categorySet.size();
        //用到的标签数量
        Integer tagNum = tagSet.size();
        //作者的文章总数量
        Long articleNum = articleMapper.selectCount(wrapper);

        MyNum number = new MyNum();
        number.setCategoryNum(categoryNum);
        number.setTagNum(tagNum);
        number.setArticleNum(articleNum);

        return number;
    }

    @Override
    public Page<Article> findPageByAuthor(Integer pageNum, Integer pageSize, String author) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if (StringUtils.isNotBlank(author)) {
            //准确查询，非模糊查询
            wrapper.eq(Article::getAuthor, author);
        }
        return articleMapper.selectPage(new Page<Article>(pageNum, pageSize), wrapper);
    }

    @Override
    public Page<Article> findPageByCategory(Integer pageNum, Integer pageSize, String category) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if (StringUtils.isNotBlank(category)) {
            wrapper.like(Article::getCategory, category);
        }
        return articleMapper.selectPage(new Page<Article>(pageNum, pageSize), wrapper);
    }

    @Override
    public String upload(MultipartFile file) throws IOException {
        //获取源文件名称
        String originalFilename = file.getOriginalFilename();
        //定义文件唯一标识（前缀）
        String flag = IdUtil.fastSimpleUUID();
        //定义文件存储的地址
        String rootFilePath = System.getProperty("user.dir") + "/files/cover/" + flag + "_" + originalFilename;
        //把文件写入到上传路径
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        //返回请求文件下载的地址
        String uploadUrl = "http://" + ip + ":" + port + "/article/" + flag;
        return uploadUrl;
    }

    @Override
    public void getFiles(String flag, HttpServletResponse response) {
        OutputStream os;  // 新建一个输出流对象
        //寻找匹配文件的地址
        ///src/main/resources/cover/
        String basePath = System.getProperty("user.dir") + "/files/cover/";  // 定于文件上传的根路径
        List<String> fileNames = FileUtil.listFileNames(basePath);  // 获取所有的文件名称
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // 找到跟参数一致的文件
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // 通过文件的路径读取文件字节流
                os = response.getOutputStream();   // 通过输出流返回文件
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("文件下载失败");
        }
    }
}
