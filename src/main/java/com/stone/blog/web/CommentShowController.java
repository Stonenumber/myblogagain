package com.stone.blog.web;

import com.stone.blog.po.Comment;
import com.stone.blog.po.User;
import com.stone.blog.service.BlogService;
import com.stone.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentShowController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PageableDefault(size = 5, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long blogId, Model model) {
        model.addAttribute("commentPage", commentService.pageListByBlogId(blogId, pageable));
        model.addAttribute("blogId", blogId);
        return "blog :: commentList";
    }

    @PostMapping("/comments/{blogId}")
    public String Postcomments(@PageableDefault(size = 5, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, @PathVariable Long blogId, Model model) {
        model.addAttribute("commentPage", commentService.pageListByBlogId(blogId, pageable));
        model.addAttribute("blogId", blogId);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
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
