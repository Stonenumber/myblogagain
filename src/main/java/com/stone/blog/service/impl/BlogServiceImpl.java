package com.stone.blog.service.impl;
import com.stone.blog.NotFoundException;
import com.stone.blog.dao.BlogMapper;
import com.stone.blog.po.Blog;
import com.stone.blog.po.BlogAndTag;
import com.stone.blog.po.Tag;
import com.stone.blog.service.BlogService;
import com.stone.blog.util.MarkdownUtils;
import com.stone.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    BlogMapper blogMapper;

    @Override
    public Blog getBlog(Long id) {
        return blogMapper.getBackBlog(id);
    } //后台获取博客

    @Override
    public Blog getDetailedBlog(Long id) {
        blogMapper.updateViews(id);
        Blog blog = blogMapper.getFrontBlog(id);
        if (blog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = blog.getContent();
        blog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));  //将Markdown格式转换成html
        return blog;
    }

    @Override
    public List<Blog> getAllBlog() {
        return blogMapper.getBackAll();
    }

    @Override
    public List<Blog> getByTypeId(Long typeId) {
        return blogMapper.getByTypeId(typeId);
    }

    @Override
    public List<Blog> getByTagId(Long tagId) {
        return blogMapper.getByTagId(tagId);
    }

    @Override
    public List<Blog> getIndexBlog() {
        return blogMapper.getFrontAll();
    }

    @Override
    public List<Blog> getAllRecommendBlog() {
        return blogMapper.getByRecommend();
    }

    @Override
    public List<Blog> getAllPrivateBlog() {
        return blogMapper.getByPrivately();
    }

    @Override
    public List<Blog> getSearchBlog(String query) {
        return blogMapper.getSearchBlog(query);
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogMapper.findGroupYear();
        Set<String> set = new HashSet<>(years);  //set去掉重复的年份
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : set) {
            map.put(year, blogMapper.findByYear(year));
        }
        return map;
    }

    @Override
    public int countBlog() {
        return blogMapper.getBackAll().size();
    }

    @Override
    public List<Blog> searchAllBlog(BlogQuery blog) {
        return blogMapper.searchAllBlog(blog);
    }

    @Override
    public List<Blog> topAppreciationBlogs(int num) {
        return blogMapper.topAppreciation(num);
    }


    @Override    //新增博客
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        //保存博客
        blogMapper.save(blog);
        //保存博客后才能获取自增的id
        Long id = blog.getId();
        //将标签的数据存到t_blogs_tag表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            //新增时无法获取自增的id,在mybatis里修改
            blogAndTag = new BlogAndTag(tag.getId(), id);
            blogMapper.saveBlogAndTag(blogAndTag);
        }
        return 1;
    }

    @Override   //编辑博客
    public int updateBlog(Blog blog) {
        blog.setUpdateTime(new Date());
        //先将原始的t_blog_tags关系删除
        blogMapper.deleteBlogTags(blog.getId());
        //将标签的数据存到t_blog_tags表中
        List<Tag> tags = blog.getTags();
        BlogAndTag blogAndTag = null;
        for (Tag tag : tags) {
            blogAndTag = new BlogAndTag(tag.getId(), blog.getId());
            blogMapper.saveBlogAndTag(blogAndTag);
        }
        return blogMapper.update(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        return blogMapper.deleteById(id);
    }

}
