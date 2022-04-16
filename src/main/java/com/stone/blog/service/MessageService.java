package com.stone.blog.service;

import com.stone.blog.po.Message;

import java.util.List;

public interface MessageService {
    List<Message> getAllMessage(); //get all parent message
    void delete(Long id);
    int add(Message message);
    List<Message> listAllMessage(); // for background;
    Message getById(Long id);
}
