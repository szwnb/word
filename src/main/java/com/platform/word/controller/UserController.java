package com.platform.word.controller;

import com.platform.word.entity.User;
import com.platform.word.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    // 注册接口
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return "注册失败：该用户名已被占用！";
        }
        userMapper.register(user);
        return "注册成功！";
    }

    // 登录接口
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User loginUser = userMapper.login(user);
        if (loginUser != null) {
            return "登录成功！欢迎 " + loginUser.getUsername();
        } else {
            return "登录失败：用户名或密码错误！";
        }
    }
}