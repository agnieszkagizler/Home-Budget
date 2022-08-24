package com.example.homebudgetsda.controller;

import com.example.homebudgetsda.dto.UserEntity;
import com.example.homebudgetsda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/users")
    public void createUser(@RequestBody UserEntity userEntity){
        userService.createUser(userEntity);
    }


}
