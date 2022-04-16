package com.stone.blog.service;

import com.stone.blog.po.Type;


import java.util.List;

public interface TypeService {

    int saveType(Type type);
    int deleteType(Long id);
    int updateType(Long id, Type type);

    Type getType(Long id);
    Type getTypeByName(String name);
    List<Type> listType(); //展示所有的type

    List<Type> getBlogType(); //包含blogs的type

}
