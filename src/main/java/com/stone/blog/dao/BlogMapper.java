package com.stone.blog.dao;

import com.stone.blog.po.Blog;
import com.stone.blog.po.BlogAndTag;
import com.stone.blog.vo.BlogQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BlogMapper{

    Blog getFrontBlog(Long id);



    List<Blog> getFrontAll();

    List<Blog> getByTypeId(Long typeId);

    List<Blog> getByTagId(Long tagId);

    List<Blog> getByRecommend();

    List<Blog> getByPrivately();

    List<Blog> getSearchBlog(String query); //根据名字或者内容搜索

    List<Blog> searchAllBlog(BlogQuery blog); //根据类型，推荐或者名字查询

    int update(Blog blog);

    void save(Blog blog);

    List<Blog> findByYear(String year);

    List<String> findGroupYear();

    void updateViews(Long id);

    int deleteById(Long id);

    void saveBlogAndTag(BlogAndTag bt);

    List<Blog> topAppreciation(int num);

    void deleteBlogTags(Long blogId);


    Blog getBackBlog(Long id);

    Blog getBackBlogTemp(Long id);

    List<Blog> getBackAll();

    List<Blog> getBackAllTemp();
}
