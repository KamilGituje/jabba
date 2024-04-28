package com.example.api.controller;

import com.example.api.model.User;
import com.example.api.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    public UserController(UserService _userService) {
        this.userService = _userService;
    }

    private final UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable int userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/users/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/users/update")
    public User updateUser(@RequestBody User user)
    {
        return userService.updateUser(user);
    }

    @DeleteMapping("/users/delete/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
    }
}
