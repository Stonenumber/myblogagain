package com.stone.blog.dao;

import com.stone.blog.po.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    //只拿顶级评论节点
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);

    //拿到某blog的所有评论
//    @Query("select c from Comment c where c.blog.getId() like ?1")
    Page<Comment> findByBlogId(Long blogId, Pageable pageable);
}
