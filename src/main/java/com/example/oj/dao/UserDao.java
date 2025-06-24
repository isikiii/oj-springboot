package com.example.oj.dao;

import com.example.oj.model.User;

public interface UserDao {
    void save(User user);
    User findByUsername(String username);
}
