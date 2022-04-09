package com.stone.blog.service.impl;

import com.stone.blog.dao.UserRepository;
import com.stone.blog.po.User;
import com.stone.blog.service.UserService;
import com.stone.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User checkUser(String username, String password) {
        //使用MD5加密password
        User user = userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

}
