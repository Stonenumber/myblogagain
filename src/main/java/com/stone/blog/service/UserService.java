package com.stone.blog.service;

import com.stone.blog.po.User;

public interface UserService {
    User checkUser(String username, String password);
}
