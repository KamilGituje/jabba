package com.example.api.controller;

import com.example.api.mapper.UserMapper;
import com.example.api.model.dto.UserDto;
import com.example.api.model.dto.UserForCreationDto;
import com.example.api.model.dto.UserForSearchByFullNameDto;
import com.example.api.model.dto.UserForUpdateDto;
import com.example.api.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    public UserController(UserService _userService, UserMapper _mapper) {
        this.userService = _userService;
        this.mapper = _mapper;
    }

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping("/users")
    public List<UserDto> getUsers() {
        return mapper.usersToUsersDto(userService.getUsers());
    }

    @GetMapping("/users/{userId}")
    public UserDto getUser(@PathVariable int userId) {
        return mapper.userToUserDto(userService.getUser(userId));
    }

    @GetMapping("/users/name")
    public List<UserDto> getUsersByFullName(@RequestBody UserForSearchByFullNameDto user) {
        return mapper.usersToUsersDto(userService.getUsersByFullName(user.getFirstName(), user.getLastName()));
    }

    @PostMapping("/users/create")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserForCreationDto userForCreationDto) {
        var user = mapper.userForCreationDtoToUser(userForCreationDto);
        user.getAddress().setUser(user);
        var userCreated = userService.createUser(user);
        return mapper.userToUserDto(userCreated);
    }

    @PutMapping("/users/update/{userId}")
    public UserDto updateUser(@RequestBody UserForUpdateDto userForUpdateDto, @PathVariable int userId) {
        var user = mapper.userForUpdateDtoToUser(userForUpdateDto);
        user.setUserId(userId);
        user.getAddress().setUser(user);
        var userUpdated = userService.updateUser(user);
        return mapper.userToUserDto(userUpdated);
    }

    @DeleteMapping("/users/delete/{userId}")
    public void deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
    }
}
