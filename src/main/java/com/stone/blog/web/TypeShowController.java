package com.stone.blog.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Blog;
import com.stone.blog.po.Type;
import com.stone.blog.service.BlogService;
import com.stone.blog.service.TypeService;
import com.stone.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @RequestParam(required = false, defaultValue = "1", value = "pageNum") int pageNum, Model model) {
        List<Type> types = typeService.getBlogType(); //得到所有的type
        //从导航点击进去，则设置为第一type
        if (id == -1) {
            id = types.get(0).getId();
        }
        BlogQuery blogQuery = new BlogQuery();
        blogQuery.setTypeId(id);
        PageHelper.startPage(pageNum, 5);
        List<Blog> allBlog = blogService.searchAllBlog(blogQuery);
        PageInfo page = new PageInfo(allBlog);
        model.addAttribute("types", types);
        model.addAttribute("page", page);
        model.addAttribute("activeTypeId", id);
        return "types";
    }
}
