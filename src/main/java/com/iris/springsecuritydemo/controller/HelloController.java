package com.iris.springsecuritydemo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @RequestMapping("/hello")
    @PreAuthorize("@ex.hasAuthority('sys:dept:list11')")
//    @PreAuthorize("hasAnyAuthority('sys:dept:list11')")
    public String hello(){
        return "hello";
    }
}
