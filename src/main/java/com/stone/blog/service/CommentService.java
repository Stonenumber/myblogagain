package com.stone.blog.service;

import com.stone.blog.po.Comment;
import java.util.List;

public interface CommentService {

    List<Comment> listCommentsByBlogId(Long blogId); //blog下一级评论和其所有的子评论

    List<Comment> listCommentsByParentComment(Comment parentComment); //某个评论下所有的子评论

    List<Comment> listComments(); //list all comments on background

    Comment findById(Long id);

    int saveComment(Comment comment);

    void deleteCommentAndChildren(Comment comment);
}
