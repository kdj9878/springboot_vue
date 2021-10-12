package com.xxx.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/user")
public class loginController {
    


    @GetMapping(value = "/login")
    public void login(){
        System.out.println("로그인 컨트롤러");
    }
}
