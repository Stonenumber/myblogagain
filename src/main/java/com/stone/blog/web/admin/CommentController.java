package com.stone.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Comment;
import com.stone.blog.service.CommentService;
//import javafx.util.converter.DefaultStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/comments")
    public String comment(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model){
        PageHelper.startPage(pageNum, 10);
        List<Comment> allComments = commentService.listComments();
        PageInfo pageInfo = new PageInfo(allComments);
        model.addAttribute("page", pageInfo);
        return "admin/comment";
    }

/*    @GetMapping("/comments/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        commentService.deleteComment(id);
        attributes.addFlashAttribute("message", "Delete success.");
        return "redirect:/admin/comments";
    }*/
    @GetMapping("/comments/{id}/deletes")
    public String deletes(@PathVariable Long id, RedirectAttributes attributes){
        Comment comment = commentService.findById(id);
        commentService.deleteCommentAndChildren(comment);
        attributes.addFlashAttribute("message", "Delete success.");
        return "redirect:/admin/comments";
    }


}
