package com.stone.blog.service.impl;

import com.stone.blog.NotFoundException;
import com.stone.blog.dao.TypeMapper;
import com.stone.blog.po.Type;
import com.stone.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    //增删改查都用事务Transactional标记
    @Transactional
    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }

    @Transactional
    @Override
    public List<Type> listType() {
        return typeMapper.getAllType();
    }

    @Transactional
    @Override
    public int updateType(Long id, Type type) {
        Type t = typeMapper.getType(id);
        if (t == null){
            throw new NotFoundException("Not exist.");
        }
        BeanUtils.copyProperties(type, t); //把type里的属性复制到t的身上
        return typeMapper.updateType(t);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeMapper.deleteType(id);
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }


    //展示数量最多的几个分类
    @Override
    public List<Type> getBlogType() {
        return typeMapper.getBlogType();
    }
}
