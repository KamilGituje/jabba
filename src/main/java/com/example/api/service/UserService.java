package com.example.api.service;

import com.example.api.Utility;
import com.example.api.mapper.UserMapper;
import com.example.api.model.Address;
import com.example.api.model.User;
import com.example.api.model.dto.UserDto;
import com.example.api.model.dto.UserForCreationDto;
import com.example.api.model.dto.UserForUpdateDto;
import com.example.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class UserService {

    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    private final UserRepository userRepository;
    private final UserMapper mapper;

    public List<UserDto> getUsers(String firstName, String lastName) {
        if (isFullNamePresent(firstName, lastName)) {
            return mapper.usersToUsersDto(userRepository.findByFirstNameAndLastName(firstName, lastName));
        } else if (firstName != null) {
            return mapper.usersToUsersDto(userRepository.findByFirstName(firstName));
        } else if (lastName != null) {
            return mapper.usersToUsersDto(userRepository.findByLastName(lastName));
        } else {
            return mapper.usersToUsersDto(userRepository.findAll());
        }
    }

    public UserDto getUser(UUID userId) {
        return mapper.userToUserDto(userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND)));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public UserDto updateUser(UserForUpdateDto userForUpdateDto, UUID userId) {
        var updatedUser = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        if (isUserValid(updatedUser)) {
            updatedUser.setFirstName(userForUpdateDto.getFirstName());
            updatedUser.setLastName(userForUpdateDto.getLastName());
            updatedUser.setBirthDate(userForUpdateDto.getBirthDate());
            updatedUser.getAddress().setCity(userForUpdateDto.getAddress().getCity());
            updatedUser.getAddress().setStreet(userForUpdateDto.getAddress().getStreet());
            updatedUser.getAddress().setBuilding(userForUpdateDto.getAddress().getBuilding());
            updatedUser.getAddress().setApartment(userForUpdateDto.getAddress().getApartment());
            updatedUser.setEmail(userForUpdateDto.getEmail());
            return mapper.userToUserDto(updatedUser);
        }
        throw new IllegalArgumentException();
    }

    public void deleteUser(UUID userId) {
        var user = getUser(userId);
        userRepository.delete(mapper.userDtoToUser(user));
    }

    public UserDto createUser(UserForCreationDto userForCreationDto) {
        var userToAdd = mapper.userForCreationDtoToUser(userForCreationDto);
        userToAdd.getAddress().setUser(userToAdd);
        if (isUserValid(userToAdd)) {
            return mapper.userToUserDto(addUser(userToAdd));
        }
        throw new IllegalArgumentException();
    }

    public boolean isUserValid(User user) {
        if (Utility.hasOnlyLetters(user.getFirstName())) {
            if (Utility.hasOnlyLetters(user.getLastName())) {
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
        if (Utility.hasOnlyLetters(address.getCity())) {
            if (Utility.hasOnlyLetters(address.getStreet())) {
                if (address.getBuilding() > 0) return true;
            }
        }
        return false;
    }

    public boolean isFullNamePresent(String firstName, String lastName) {
        if (firstName != null) {
            if (lastName != null) {
                return true;
            }
        }
        return false;
    }
}
