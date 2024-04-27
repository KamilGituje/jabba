package com.example.api.controller;

import com.example.api.model.User;
import com.example.api.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    public UserController(UserService _userService)
    {
        this.userService = _userService;
    }
    private final UserService userService;

@GetMapping("/users")
    public List<User> getUsers()
{
    return userService.getUsers();
}
}
