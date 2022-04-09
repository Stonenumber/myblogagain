package com.stone.blog.service;

import com.stone.blog.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {

    List<Comment> listCommentsByBlogId(Long blogId);
    Comment saveComment(Comment comment);

    List<Comment> listComments();

    void deleteComment(Long id);

    List<Comment> listChildrenComments(Comment comment);

    Comment findById(Long id);

    void deleteCommentAndChildren(Comment comment);

    Page<Comment> pageList(Pageable pageable);

    Page<Comment> pageListByBlogId(Long blogId, Pageable pageable);
}
