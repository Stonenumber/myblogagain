package com.stone.blog.po;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Message {
    private Long id;
    private String name;
    private Date createTime;
    private String content;

    private Long parentMessageId;
    private boolean admin;

    private List<Message> replyMessages;
}
