package com.iris.springsecuritydemo.controller;

import com.iris.springsecuritydemo.domain.ResponseResult;
import com.iris.springsecuritydemo.domain.User;
import com.iris.springsecuritydemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        return loginService.login(user);

    }
    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();

    }
}
