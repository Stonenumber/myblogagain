package com.stone.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Tag;
import com.stone.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags") //get all tags
    public String tags(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model){
        PageHelper.startPage(pageNum, 8);
        List<Tag> allTag = tagService.getAllTag();
        PageInfo pageInfo = new PageInfo(allTag);
        model.addAttribute("page", pageInfo);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model, Tag tag){
        model.addAttribute("tag", tag);
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String inputById(@PathVariable Long id, Model model){
        Tag t = tagService.getTag(id);
        model.addAttribute("tag", t);
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteById(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "Delete success.");
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags") // save or update tag
    public String postTags(Tag tag, BindingResult result, RedirectAttributes attributes){
       Tag t =  tagService.getTagByName(tag.getName());
       if(t != null){
           result.rejectValue("name", "nameError", "Tags already existed.");
       }
       if(result.hasErrors()){
           return "admin/tags-input";
       }
       int t1 = tagService.saveTag(tag);
       if(t1 == 1){
           attributes.addFlashAttribute("message", "Success");
       }else{
           attributes.addFlashAttribute("massage", "Fail");
       }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}") // update tag by id
    public String postTagsById(@PathVariable Long id, Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if(t != null){
            result.rejectValue("name", "nameError", "Tags already existed.");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        int t1 = tagService.updateTag(tag);
        if(t1 == 1){
            attributes.addFlashAttribute("message", "Success");
        }else{
            attributes.addFlashAttribute("message", "Fail");
        }
        return "redirect:/admin/tags";
    }





}
