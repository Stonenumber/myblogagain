package com.stone.blog.service;

import com.stone.blog.po.Tag;
import java.util.List;

public interface TagService {

    int saveTag(Tag tag);

    int deleteTag(Long id);

    int updateTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> getAllTag();

    List<Tag> getTagByString(String ids); //根据ids字符串包含一系列tag

    List<Tag> getBlogTag(); // tag包含blog

}
