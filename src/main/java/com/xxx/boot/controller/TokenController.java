package com.xxx.boot.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/token")
public class TokenController {
    

    @GetMapping(value = "/getToken")
    public void getToken(){

        
    }
}
