package com.stone.blog.service;

import com.stone.blog.po.Blog;
import com.stone.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);
    Blog getAndConvert(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    Page<Blog> listBlog(Long tagId, Pageable pageable);
    Page<Blog> listBlog(String query, Pageable pageable);
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
    List<Blog> listRecommendTopBlog(Integer size);
    Map<String, List<Blog>> archiveBlog();
    Long countBlog();

    Page<Blog> listBlogByPrivately(Pageable pageable);
}
