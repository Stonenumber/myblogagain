package com.stone.blog.web.admin;

import com.stone.blog.po.Tag;
import com.stone.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC)Pageable pageable, Model model){
        model.addAttribute("page", tagService.pageList(pageable));
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

    @PostMapping("/tags")
    public String postTags(@Valid Tag tag, BindingResult result, RedirectAttributes attributes){
       Tag t =  tagService.getTagByName(tag.getName());
       if(t != null){
           result.rejectValue("name", "nameError", "Tags already existed.");
       }
       if(result.hasErrors()){
           return "admin/tags-input";
       }
       Tag t1 = tagService.saveTag(tag);
       if(t1 != null){
           attributes.addFlashAttribute("message", "Success");
       }else{
           attributes.addFlashAttribute("massage", "Fail");
       }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String postTagsById(@PathVariable Long id, @Valid Tag tag, BindingResult result, RedirectAttributes attributes){
        Tag t = tagService.getTagByName(tag.getName());
        if(t != null){
            result.rejectValue("name", "nameError", "Tags already existed.");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t1 = tagService.updateTag(id, tag);
        if(t1 != null){
            attributes.addFlashAttribute("message", "Success");
        }else{
            attributes.addFlashAttribute("message", "Fail");
        }
        return "redirect:/admin/tags";
    }





}
