package com.stone.blog.web;

import com.stone.blog.po.User;
import com.stone.blog.service.BlogService;
import com.stone.blog.service.TagService;
import com.stone.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model, HttpSession session) {
        model.addAttribute("types", typeService.listTypeTop(4));
        model.addAttribute("tags", tagService.listTopTag(4));
        model.addAttribute("RecommendBlogs", blogService.listRecommendTopBlog(4));
        User user = (User) session.getAttribute("user");
        if(user != null){
            model.addAttribute("page", blogService.listBlog(pageable));
        }else{
            model.addAttribute("page", blogService.listBlogByPrivately(pageable));
        }
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, @RequestParam String query){
        model.addAttribute("page", blogService.listBlog("%" + query + "%", pageable));
        model.addAttribute("query", query);
        return "search";
    }



    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs", blogService.listRecommendTopBlog(3));
        return "fragments :: newBlogList";
    }

}
