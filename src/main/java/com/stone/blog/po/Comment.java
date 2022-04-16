package com.stone.blog.po;

import ch.qos.logback.core.pattern.color.BoldGreenCompositeConverter;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
public class Comment {

    private Long id;
    private String nickname;
    private String avatar; //头像
    private String email;
    private String content;

    private Date createTime;
    private boolean adminComment;//是否为管理员评论

    private Long blogId; //对应的blog
    private Long parentCommentId; //对应的父评论，可为null

    private List<Comment> replyComments; // 子评论
}
