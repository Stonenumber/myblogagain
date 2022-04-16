package com.stone.blog.web.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stone.blog.po.Blog;
import com.stone.blog.po.User;
import com.stone.blog.service.BlogService;
import com.stone.blog.service.TagService;
import com.stone.blog.service.TypeService;
import com.stone.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs") //展示所有的blog
    public String blogs(@RequestParam(required = false, defaultValue = "1", value = "pageNum")int pageNum,
                        Model model){
        PageHelper.startPage(pageNum, 30);
        List<Blog> allBlog = blogService.getAllBlog();
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("page", pageInfo);
        model.addAttribute("types", typeService.listType());
        return LIST;
    }

    @PostMapping("/blogs/search") //根据名字，类型查询所有blog
    public String search(@RequestParam(required = false, defaultValue = "1", value = "pageNum")int pageNum,
                         BlogQuery blog, Model model){
        PageHelper.startPage(pageNum, 10);
        List<Blog> allBlog = blogService.searchAllBlog(blog);
        PageInfo pageInfo = new PageInfo(allBlog);
        model.addAttribute("page", pageInfo);
        return "admin/blogs :: blogList"; //blogs页面下blogList的片段
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.getAllTag());
        model.addAttribute("blog", new Blog());
        return INPUT;
    }

    @PostMapping("/blogs")
    public String postBlogs(Blog blog, RedirectAttributes attributes, HttpSession session){
        User user = (User) session.getAttribute("user");
        blog.setUserId(user.getId());
        blog.setType(typeService.getType(blog.getTypeId()));
        blog.setTags(tagService.getTagByString(blog.getTagIds()));
        int b;
        if(blog.getId() == null){
            b = blogService.saveBlog(blog);
        }else{
            b = blogService.updateBlog(blog);
        }


        if(b == 1){
            attributes.addFlashAttribute("message","Success");
        }else{
            attributes.addFlashAttribute("message", "Fail");
        }
        return REDIRECT;
    }

    @GetMapping("/blogs/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.getAllTag());
        Blog b = blogService.getBlog(id);
        b.init();
        model.addAttribute("blog", b);
        return INPUT;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "Delete Done");
        return REDIRECT;
    }

}
