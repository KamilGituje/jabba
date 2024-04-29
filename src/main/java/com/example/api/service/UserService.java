package com.example.api.service;

import com.example.api.Utility;
import com.example.api.model.Address;
import com.example.api.model.User;
import com.example.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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
        return userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    public List<User> getUsersByFullName(String firstName, String lastName)
    {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User user) {
        if (isUserValid(user)) {
            var updatedUser = getUser(user.getUserId());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setBirthDate(user.getBirthDate());
            updatedUser.setAddress(user.getAddress());
            updatedUser.setEmail(user.getEmail());
            return updatedUser;
        }
        throw new IllegalArgumentException();
    }

    public void deleteUser(int userId) {
        var user = getUser(userId);
        userRepository.delete(user);
    }

    public User createUser(User user) {
        if (isUserValid(user)) {
            return addUser(user);
        }
        throw new IllegalArgumentException();
    }

    public boolean isUserValid(User user) {
        if (StringUtils.hasText(user.getFirstName()) && Utility.hasOnlyLetters(user.getFirstName())) {
            if (StringUtils.hasText(user.getLastName()) && Utility.hasOnlyLetters(user.getLastName())) {
                if (user.getBirthDate() != null) {
                    if (isAddressValid(user.getAddress())) {
                        if (StringUtils.hasText(user.getEmail())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isAddressValid(Address address) {
        if (StringUtils.hasText(address.getCity()) && Utility.hasOnlyLetters(address.getCity())) {
            if (StringUtils.hasText(address.getStreet()) && Utility.hasOnlyLetters(address.getStreet())) {
                if (address.getBuilding() > 0) return true;
            }
        }
        return false;
    }
}
