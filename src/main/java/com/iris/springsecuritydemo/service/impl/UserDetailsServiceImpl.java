package com.iris.springsecuritydemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iris.springsecuritydemo.domain.LoginUser;
import com.iris.springsecuritydemo.domain.User;
import com.iris.springsecuritydemo.mapper.MenuMapper;
import com.iris.springsecuritydemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    private List<String> permissions;



    public UserDetails loadUserByUsername1(String s) throws UsernameNotFoundException {
        // 数据库中查询用户信息
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, s);
        User user = userMapper.selectOne(wrapper);
        // 如果查询不到数据就通过抛异常来给出提示
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }

        List<String> auth = menuMapper.getPermissionsByUserId(user.getId());

        return new LoginUser(user, auth);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(wrapper);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户名或密码错误");
        }
        // 查询权限信息
        List<String> permissionsByUserId = menuMapper.getPermissionsByUserId(user.getId());

        LoginUser loginUser = new LoginUser(user, permissionsByUserId);

        return loginUser;
    }
}
