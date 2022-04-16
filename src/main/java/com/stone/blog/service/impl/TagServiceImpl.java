package com.stone.blog.service.impl;

import com.stone.blog.NotFoundException;
import com.stone.blog.dao.TagMapper;
import com.stone.blog.po.Tag;
import com.stone.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Transactional
    @Override
    public int saveTag(Tag tag) {
        return tagMapper.saveTag(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagMapper.getTag(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagMapper.getTagByName(name);
    }

    @Transactional
    @Override
    public List<Tag> getAllTag() {
        return tagMapper.getAllTag();
    }


    @Transactional
    @Override
    public List<Tag> getTagByString(String ids) { //1,2,3
        List<Tag> res = new ArrayList<>();
        if(ids == null || ids.length() == 0) return res;
        String[] array = ids.split(",");
        for(String sub : array){
            Long id = new Long(sub);
            res.add(tagMapper.getTag(id));
        }
        return res;
    }

    @Override
    public List<Tag> getBlogTag() {
        return tagMapper.getBlogTag();
    }

    @Transactional
    @Override
    public int updateTag(Tag tag) {
        Tag t = tagMapper.getTag(tag.getId());
        if (t == null) {
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag,t);
        return tagMapper.updateTag(t);
    }

    @Override
    public int deleteTag(Long id) {
        return tagMapper.deleteTag(id);
    }
}
