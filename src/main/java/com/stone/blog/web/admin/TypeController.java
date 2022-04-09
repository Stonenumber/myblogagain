package com.stone.blog.web.admin;

import com.stone.blog.po.Type;
import com.stone.blog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    //页面大小，排序
    @GetMapping("/types")
    public String types(@PageableDefault(size = 4, sort = {"id"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";

    }

    //result得到后台判断结果
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes){
        //如果该分类存在，则往result里放提示
        Type t1 = typeService.getTypeByName(type.getName());
        if(t1 != null){
            result.rejectValue("name", "name error", "The type already existed.");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if(t == null){
            attributes.addFlashAttribute("message", "Save Fail");
        }else{
            attributes.addFlashAttribute("message", "Save Success");

        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String post(@Valid Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        //如果该分类存在，则往result里放提示
        Type t1 = typeService.getTypeByName(type.getName());
        if(t1 != null){
            result.rejectValue("name", "name error", "The type already existed.");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if(t == null){
            attributes.addFlashAttribute("message", "Update Fail");
        }else{
            attributes.addFlashAttribute("message", "Update Success");

        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "Delete Success.");
        return "redirect:/admin/types";
    }


}
