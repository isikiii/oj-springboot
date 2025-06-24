package com.example.oj.controller;

import com.example.oj.dao.UserDao;
import com.example.oj.model.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setRole("USER");
        userDao.save(user);
        return "注册成功";
    }

    @GetMapping("/{username}")
    public User getByUsername(@PathVariable String username) {
        return userDao.findByUsername(username);
    }

}

