package com.example.api.mapper;

import com.example.api.model.User;
import com.example.api.model.dto.UserDto;
import com.example.api.model.dto.UserForCreationDto;
import com.example.api.model.dto.UserForUpdateDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserForCreationDto userToUserForCreationDto(User user);

    User userForCreationDtoToUser(UserForCreationDto userForCreationDto);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    List<UserDto> usersToUsersDto(List<User> users);

    User userForUpdateDtoToUser(UserForUpdateDto userForUpdateDto);
}
