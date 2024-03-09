package com.iris.springsecuritydemo.service;

import com.iris.springsecuritydemo.domain.ResponseResult;
import com.iris.springsecuritydemo.domain.User;

public interface LoginService {
    public ResponseResult login(User user);
    public ResponseResult logout();
}
