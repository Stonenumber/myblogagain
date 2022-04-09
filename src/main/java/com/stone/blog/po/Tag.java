package com.stone.blog.po;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Tag {

    private Long id;
    private String name;
    private List<Blog> blogs = new ArrayList<>();

    public List<Blog> getBlogs() {
        return blogs;
    }
    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
