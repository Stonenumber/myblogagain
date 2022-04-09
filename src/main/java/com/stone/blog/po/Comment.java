package com.stone.blog.po;

import ch.qos.logback.core.pattern.color.BoldGreenCompositeConverter;
import lombok.Data;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Comment {

    private Long id;
    private String nickname;
    private String email;
    private String content;
    private String avatar; //头像
    private Blog blog;
    //一个评论有很多子评论
    private List<Comment> replyComments = new ArrayList<>();
    //很多个子评论对应一个父评论
    private Comment parentComment;
    private Date createTime;
    private boolean adminComment;

    public boolean isAdminComment() {
        return adminComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", blog=" + blog +
                ", replyComments=" + replyComments +
                ", parentComment=" + parentComment +
                ", createTime=" + createTime +
                ", adminComment=" + adminComment +
                '}';
    }
}
