package com.stone.blog.dao;

import com.stone.blog.po.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;

@Mapper
public interface CommentMapper {

    //查询某个blog的一级评论，即parentCommentId为null
    List<Comment> findByBlogId(@Param("blogId") Long blogId);

    //根据父评论查询所有子评论
    List<Comment> findByParentCommentId(@Param("parentCommentId")Long parentCommentId);

    List<Comment> listComments();

    int saveComment(Comment comment);

    int deleteByCommentId(Long id);

    Comment findByCommentId(Long id);

//    int deleteComment(List<Long> ids);
}
