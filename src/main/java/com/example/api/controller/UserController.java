package com.example.api.controller;

import com.example.api.mapper.UserMapper;
import com.example.api.model.User;
import com.example.api.model.dto.UserDto;
import com.example.api.model.dto.UserForCreationDto;
import com.example.api.model.dto.UserForUpdateDto;
import com.example.api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("users")
public class UserController {

    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers(@RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName) {
        var users = userService.getUsers(firstName, lastName);
        if(!users.isEmpty()) {
            return ResponseEntity.ok(users);
        }
        throw new ResponseStatusException(NOT_FOUND);
    }

    @GetMapping("{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserForCreationDto userForCreationDto) {
        var userCreated = userService.createUser(userForCreationDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{userId}").buildAndExpand(userCreated.getUserId()).toUri()).body(userCreated);
    }

    @PutMapping("{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserForUpdateDto userForUpdateDto, @PathVariable UUID userId) {
        return ResponseEntity.ok(userService.updateUser(userForUpdateDto, userId));
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
