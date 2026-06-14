package com.platform.word.mapper;

import com.platform.word.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    // 注册：把新账号密码塞进数据库
    @Insert("INSERT INTO user(username, password) VALUES(#{username}, #{password})")
    void register(User user);

    // 登录：去数据库查有没有这个账号和对应的密码
    @Select("SELECT * FROM user WHERE username = #{username} AND password = #{password}")
    User login(User user);

    // 检查用户名是否已经被注册过
    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(String username);
}