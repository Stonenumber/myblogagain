package com.stone.blog.service.impl;

import com.stone.blog.dao.MessageMapper;
import com.stone.blog.po.Message;
import com.stone.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<Message> getAllMessage() {
        List<Message> parentMessage = messageMapper.getAllParentMessage();
        for(Message message : parentMessage){
            List<Message> subMessage = getAllSubMessage(message);
            message.setReplyMessages(subMessage);
        }
        return parentMessage;
    }

    private List<Message> getAllSubMessage(Message message){
        List<Message> res = new ArrayList<>();
        Queue<Message> queue = new LinkedList<>();
        queue.offer(message);
        while(!queue.isEmpty()){
            Message cur = queue.poll();
            List<Message> sub = messageMapper.getByParentMessageId(cur.getId());
            for(Message mes : sub){
                queue.offer(mes);
            }
            if(!cur.equals(message)) res.add(cur);
        }
        return res;
    }



    @Override
    public void delete(Long id) {
        Message message =  messageMapper.getById(id);
        List<Message> sub = getAllSubMessage(message);
        for(int i = sub.size() - 1; i >= 0; i--){
            Message cur = sub.get(i);
            messageMapper.delete(cur.getId());
        }
    }

    @Override
    public int add(Message message) {
        return messageMapper.insert(message);
    }

    @Override
    public List<Message> listAllMessage() {
        return messageMapper.listMessages();
    }

    @Override
    public Message getById(Long id) {
        return messageMapper.getById(id);
    }


}
