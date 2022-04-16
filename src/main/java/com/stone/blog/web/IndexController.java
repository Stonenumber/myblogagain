package com.stone.blog.web;

import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Blog;
import com.stone.blog.po.Tag;
import com.stone.blog.po.Type;
import com.stone.blog.po.User;
import com.stone.blog.service.BlogService;
import com.stone.blog.service.TagService;
import com.stone.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.github.pagehelper.PageHelper;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "1", value = "pageNum")int pageNum, Model model, HttpSession session) {
        List<Blog> recommendBlog =blogService.getAllRecommendBlog();  //获取推荐博客
        List<Type> allType = typeService.getBlogType();  //获取博客的类型(联表查询)
        List<Tag> allTag = tagService.getBlogTag();  //获取博客的标签(联表查询)
        User user = (User) session.getAttribute("user");

        List<Blog> allBlog = null;
        PageHelper.startPage(pageNum, 6);
        if(user == null){
            allBlog =  blogService.getIndexBlog(); //前端博客展示
        }else{
            allBlog = blogService.getAllPrivateBlog(); //隐私博客一起显示
        }

        //得到分页结果对象
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("tags", allTag);
        model.addAttribute("types", allType);
        model.addAttribute("recommendBlog", recommendBlog);
        return "index";

    }

    @PostMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "1", value = "pageNum")int pageNum, Model model, @RequestParam String query){
        PageHelper.startPage(pageNum, 8);
        List<Blog> allBlog = blogService.getSearchBlog(query);
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }


    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getDetailedBlog(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs", blogService.topAppreciationBlogs(3));
        return "fragments :: newBlogList";
    }

}
