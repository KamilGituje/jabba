package com.example.api.controller;

import com.example.api.mapper.UserMapper;
import com.example.api.model.User;
import com.example.api.model.dto.UserDto;
import com.example.api.model.dto.UserForCreationDto;
import com.example.api.model.dto.UserForUpdateDto;
import com.example.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController {
    public UserController(UserService userService, UserMapper mapper) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        return ResponseEntity.ok(userService.getUsers(firstName, lastName));
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserForCreationDto userForCreationDto) {
        var userCreated = userService.createUser(userForCreationDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(userCreated.getUserId()).toUri()).body(userCreated);
    }

    @PutMapping("{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserForUpdateDto userForUpdateDto, @PathVariable UUID userId) {
        return ResponseEntity.ok(userService.updateUser(userForUpdateDto, userId));
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
    }
}
