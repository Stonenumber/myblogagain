package com.stone.blog.web.admin;

import com.stone.blog.po.Comment;
import com.stone.blog.service.CommentService;
//import javafx.util.converter.DefaultStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public String comment(@PageableDefault(size=5, sort = {"createTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
//        model.addAttribute("comments", commentService.listComments());
        model.addAttribute("page", commentService.pageList(pageable));
        return "admin/comment";
    }

    @GetMapping("/comments/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        commentService.deleteComment(id);
        attributes.addFlashAttribute("message", "Delete success.");
        return "redirect:/admin/comments";
    }
    @GetMapping("/comments/{id}/deletes")
    public String deletes(@PathVariable Long id, RedirectAttributes attributes){
        Comment comment = commentService.findById(id);
        commentService.deleteCommentAndChildren(comment);
        attributes.addFlashAttribute("message", "Delete success.");
        return "redirect:/admin/comments";
    }


}
