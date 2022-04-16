package com.stone.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Comment;
import com.stone.blog.po.Message;
import com.stone.blog.po.User;
import com.stone.blog.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class MessageShowController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/messages")
    public String messages(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model){
        PageHelper.startPage(pageNum, 10);
        List<Message> allMessages = messageService.getAllMessage();
        PageInfo page = new PageInfo(allMessages);
        model.addAttribute("messagePage", page);
        return "about :: messageList";
    }

    @PostMapping("/messages")
    public String postMessages(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model){
        PageHelper.startPage(pageNum, 10);
        List<Message> allMessages = messageService.getAllMessage();
        PageInfo page = new PageInfo(allMessages);
        model.addAttribute("messagePage", page);
        return "about :: messageList";
    }

    @PostMapping("/messages/add")
    public String postMessage(Message message, HttpSession session){
        User user = (User)session.getAttribute("user");
        Long pm = message.getParentMessageId();
        if(pm != -1){
            Message parent = messageService.getById(pm);
            String content = parent.getContent();
            StringBuilder sb = new StringBuilder();
            sb.append("@");
            sb.append(parent.getName() + " ");
            sb.append(content);
            parent.setContent(sb.toString());
        }
        if(user != null){
            message.setAdmin(true);
        }
        message.setCreateTime(new Date());
        messageService.add(message);
        return "redirect:/messages";
    }

}
