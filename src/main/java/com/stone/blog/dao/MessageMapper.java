package com.stone.blog.dao;

import com.stone.blog.po.Comment;
import com.stone.blog.po.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {

    List<Message> getAllParentMessage();

    List<Message> getByParentMessageId(Long id);

    Message getById(Long id);

    int delete(Long id);

    int insert(Message message);

    List<Message> listMessages();
}
