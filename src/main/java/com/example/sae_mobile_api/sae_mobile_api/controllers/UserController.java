package com.example.sae_mobile_api.sae_mobile_api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.sae_mobile_api.sae_mobile_api.models.Role;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/user")
public class UserController extends PersonneController {
    @GetMapping("/abc")
    public String aaa(){
        return  "test from user";
    }
    @Override
    protected String getRole() {
        // TODO Auto-generated method stub
        return Role.USER.name();
    }

}
