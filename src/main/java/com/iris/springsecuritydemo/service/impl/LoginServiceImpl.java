package com.iris.springsecuritydemo.service.impl;

import com.alibaba.fastjson.JSON;
import com.iris.springsecuritydemo.domain.LoginUser;
import com.iris.springsecuritydemo.domain.ResponseResult;
import com.iris.springsecuritydemo.domain.User;
import com.iris.springsecuritydemo.service.LoginService;
import com.iris.springsecuritydemo.utils.JwtUtil;
import com.iris.springsecuritydemo.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private RedisCache redisCache;

    @Autowired
    private StringRedisTemplate redisTemplate;



    public ResponseResult login(User user){
        //
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (!authenticate.isAuthenticated()){
            throw new RuntimeException("用户名密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userid = loginUser.getUser().getId();
        // 存redis
        redisTemplate.opsForValue().set("login:"+userid, JSON.toJSONString(loginUser));
        Map<String, String> map = new HashMap<>();
        String token = JwtUtil.createJWT(String.valueOf(userid));
        map.put("token", token);
        return new ResponseResult(200, map);

    }



    public ResponseResult login1(User user) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (!authenticate.isAuthenticated()){
            throw new RuntimeException("用户名密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long userid = loginUser.getUser().getId();
        String token = JwtUtil.createJWT(String.valueOf(userid));
        HashMap<String, String> map = new HashMap<>();
        map.put("token", token);
        redisTemplate.opsForValue().set("login:"+userid, JSON.toJSONString(loginUser));
        System.out.println("==============");
        String s = redisTemplate.opsForValue().get("login:" + userid);
        System.out.println(s);
        return new ResponseResult(200, map);
    }

    @Override
    public ResponseResult logout() {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long id = loginUser.getUser().getId();
        redisTemplate.delete("login:"+ id);
        return new ResponseResult(200, "注销成功");
    }
}
