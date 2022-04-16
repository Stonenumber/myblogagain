package com.stone.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Comment;
import com.stone.blog.po.User;
import com.stone.blog.service.BlogService;
import com.stone.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentShowController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, @PathVariable Long blogId, Model model) {
        PageHelper.startPage(pageNum, 4);
        List<Comment> allComments = commentService.listCommentsByBlogId(blogId);
        PageInfo pageInfo = new PageInfo(allComments);
        model.addAttribute("commentPage", pageInfo);
        model.addAttribute("blogId", blogId);
        return "blog :: commentList";
    }

    @PostMapping("/comments/{blogId}")
    public String Postcomments(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, @PathVariable Long blogId, Model model) {
        PageHelper.startPage(pageNum, 4);
        List<Comment> allComments = commentService.listCommentsByBlogId(blogId);
        PageInfo pageInfo = new PageInfo(allComments);
        model.addAttribute("commentPage", pageInfo);
        model.addAttribute("blogId", blogId);
        return "blog :: commentList";
    }

    @PostMapping("/comments") // save comment, than direct to /comments/blogId
    public String post(Comment comment, HttpSession session) {
        Long parentCommentId = comment.getParentCommentId();
        if(parentCommentId != -1){
            Comment parent = commentService.findById(parentCommentId);
            String content = comment.getContent();
            StringBuilder sb = new StringBuilder();
            sb.append("@");
            sb.append(parent.getNickname() + " ");
            sb.append(content);
            comment.setContent(sb.toString());
        }
        Long blogId = comment.getBlogId();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }


}
