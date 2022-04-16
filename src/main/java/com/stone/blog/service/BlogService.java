package com.stone.blog.service;

import com.stone.blog.po.Blog;
import com.stone.blog.vo.BlogQuery;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);  //后台展示博客

    Blog getDetailedBlog(Long id);  //前端展示博客

    List<Blog> getAllBlog(); //后台所有博客

    List<Blog> getByTypeId(Long typeId);  //根据类型id获取博客

    List<Blog> getByTagId(Long tagId);  //根据标签id获取博客

    List<Blog> getIndexBlog();  //主页博客展示，不包括私人

    List<Blog> getAllRecommendBlog();  //推荐博客展示

    List<Blog> getAllPrivateBlog(); //包括私人博客

    List<Blog> getSearchBlog(String query);  //全局搜索博客

    Map<String,List<Blog>> archiveBlog();  //归档博客

    int countBlog();  //查询博客条数

    int saveBlog(Blog blog);

    int updateBlog(Blog blog);

    int deleteBlog(Long id);

    List<Blog> searchAllBlog(BlogQuery blog);  //后台根据标题、分类、推荐搜索博客

    List<Blog> topAppreciationBlogs(int num);
}
