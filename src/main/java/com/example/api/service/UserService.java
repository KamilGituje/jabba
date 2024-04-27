package com.example.api.service;

import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
public UserService(UserRepository _userRepository)
{
    this.userRepository = _userRepository;
}
private final UserRepository userRepository;

public List<User> getUsers()
{
    return userRepository.findAll();
}
}
