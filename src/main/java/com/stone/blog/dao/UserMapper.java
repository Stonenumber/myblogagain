package com.stone.blog.dao;

import com.stone.blog.po.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper {
    User queryByUsernameAndPassword(String username, String password);
}
