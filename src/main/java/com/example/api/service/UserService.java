package com.example.api.service;

import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public UserService(UserRepository _userRepository) {
        this.userRepository = _userRepository;
    }

    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(int userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        var updatedUser = userRepository.findById(user.getUserId()).orElseThrow();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setBirthDate(user.getBirthDate());
        updatedUser.setAddress(user.getAddress());
        updatedUser.setEmail(user.getEmail());
        return updatedUser;
    }

    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }
}
