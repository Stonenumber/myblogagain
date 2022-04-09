package com.stone.blog.po;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Type {

    private Long id;
    private String name;
    //一个Type对应多个Blog，为关系被维护端
    private List<Blog> blogs = new ArrayList<>();

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
