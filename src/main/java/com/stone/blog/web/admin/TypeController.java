package com.stone.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Type;
import com.stone.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    //页面大小，排序
    @GetMapping("/types") //get all types
    public String types(@RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum,
                        Model model){
        PageHelper.startPage(pageNum, 10);
        List<Type> allType = typeService.listType();
        PageInfo pageInfo = new PageInfo(allType);
        model.addAttribute("page", pageInfo);
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
    public String post(Type type, BindingResult result, RedirectAttributes attributes){
        //如果该分类存在，则往result里放提示
        Type t1 = typeService.getTypeByName(type.getName());
        if(t1 != null){
            result.rejectValue("name", "name error", "The type already existed.");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        int t = typeService.saveType(type);
        if(t != 1){
            attributes.addFlashAttribute("message", "Save Fail");
        }else{
            attributes.addFlashAttribute("message", "Save Success");

        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}") // change type by id
    public String post(Type type, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        //如果该分类存在，则往result里放提示
        Type t1 = typeService.getTypeByName(type.getName());
        if(t1 != null){
            result.rejectValue("name", "name error", "The type already existed.");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        int t = typeService.updateType(id, type);
        if(t == 1){
            attributes.addFlashAttribute("message", "Update Success");
        }else{
            attributes.addFlashAttribute("message", "Update Fail");
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
