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
import com.example.springboot.entity.Tag;
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
            return Result.error("-1","??????????????????");
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
        //??????????????????
        Integer categoryNum = categorySet.size();
        //?????????????????????
        Integer tagNum = tagSet.size();
        //????????????????????????
        Long articleNum = articleMapper.selectCount(wrapper);

        MyNum number = new MyNum();
        number.setCategoryNum(categoryNum);
        number.setTagNum(tagNum);
        number.setArticleNum(articleNum);

        return number;
    }

    @Override
    public HashSet<String> findAllCategory(){
        HashSet<String> category = new HashSet<String>();
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("category");
        List<Article> list = articleMapper.selectList(queryWrapper);
        for (Article article: list) {
            category.add(article.getCategory());
        }
        return category;
    }

    @Override
    public ArrayList<Tag> findAllTag(){
        ArrayList<Tag> tagList = new ArrayList<>();
        HashSet<String> tag = new HashSet<String>();
        String[] types = {"success","danger","info","warning",""};
        String[] sizes = {"mini","small","medium"};
        String[] effects = {"dark","light","plain"};
        Random random = new Random();
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("tag");
        List<Article> list = articleMapper.selectList(queryWrapper);
        for (Article article: list) {
            String[] tags = article.getTag().split(",");
            for (int i = 0; i < tags.length; i ++)
            {
                tag.add(tags[i]);
            }
        }
        for(String item:tag){
            Tag tag1 = new Tag();
            tag1.setTagName(item);
            int rand = random.nextInt(5);
            tag1.setType(types[rand]);
            int rand1 = random.nextInt(3);
            tag1.setSize(sizes[rand1]);
            int rand2 = random.nextInt(3);
            tag1.setEffect(effects[rand2]);
            tagList.add(tag1);
        }
        return tagList;
    }

    @Override
    public Page<Article> findPageByAuthor(Integer pageNum, Integer pageSize, String author) {
        LambdaQueryWrapper<Article> wrapper = Wrappers.<Article>lambdaQuery();
        if (StringUtils.isNotBlank(author)) {
            //??????????????????????????????
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
        //?????????????????????
        String originalFilename = file.getOriginalFilename();
        //????????????????????????????????????
        String flag = IdUtil.fastSimpleUUID();
        //???????????????????????????
        String rootFilePath = System.getProperty("user.dir") + "/files/cover/" + flag + "_" + originalFilename;
        //??????????????????????????????
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        //?????????????????????????????????
        String uploadUrl = "http://" + ip + ":" + port + "/article/" + flag;
        return uploadUrl;
    }

    @Override
    public ArrayList<String> uploads(MultipartFile[] file) throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        for(int i=0;i<file.length;i++){
            //?????????????????????
            String originalFilename = file[i].getOriginalFilename();
            //????????????????????????????????????
            String flag = IdUtil.fastSimpleUUID();
            //???????????????????????????
            String rootFilePath = System.getProperty("user.dir") + "/files/article/" + flag + "_" + originalFilename;
            //??????????????????????????????
            FileUtil.writeBytes(file[i].getBytes(), rootFilePath);
            //?????????????????????????????????
            String uploadUrl = "http://" + ip + ":" + port + "/article/files/" + flag;
            list.add(uploadUrl);
        }
        return list;
    }

    @Override
    public void getFiles(String flag, HttpServletResponse response) {
        OutputStream os;  // ???????????????????????????
        //???????????????????????????
        ///src/main/resources/cover/
        String basePath = System.getProperty("user.dir") + "/files/cover/";  // ??????????????????????????????
        List<String> fileNames = FileUtil.listFileNames(basePath);  // ???????????????????????????
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // ??????????????????????????????
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // ??????????????????????????????????????????
                os = response.getOutputStream();   // ???????????????????????????
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("??????????????????");
        }
    }

    @Override
    public void getArticleFiles(String flag, HttpServletResponse response) {
        OutputStream os;  // ???????????????????????????
        //???????????????????????????
        ///src/main/resources/cover/
        String basePath = System.getProperty("user.dir") + "/files/article/";  // ??????????????????????????????
        List<String> fileNames = FileUtil.listFileNames(basePath);  // ???????????????????????????
        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");  // ??????????????????????????????
        try {
            if (StrUtil.isNotEmpty(fileName)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath + fileName);  // ??????????????????????????????????????????
                os = response.getOutputStream();   // ???????????????????????????
                os.write(bytes);
                os.flush();
                os.close();
            }
        } catch (Exception e) {
            System.out.println("??????????????????");
        }
    }
}
