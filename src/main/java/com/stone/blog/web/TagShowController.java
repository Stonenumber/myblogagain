package com.stone.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Blog;
import com.stone.blog.po.Tag;
import com.stone.blog.service.BlogService;
import com.stone.blog.service.TagService;
import com.stone.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}") //展示最常使用的tag
    public String tags(@RequestParam(required=false, defaultValue="1", value="pageNum")int pageNum, Model model, @PathVariable Long id) {
        List<Tag> tags = tagService.getBlogTag();
        if (id == -1) {
            id = tags.get(0).getId();
        }
        PageHelper.startPage(pageNum, 7);
        List<Blog> allBlog = blogService.getByTagId(id);
        PageInfo page = new PageInfo(allBlog);
        model.addAttribute("page", page);
        model.addAttribute("tags", tags);
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
