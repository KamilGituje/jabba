package com.example.api.service;

import com.example.api.mapper.UserMapper;
import com.example.api.model.Address;
import com.example.api.model.User;
import com.example.api.model.dto.UserForCreationDto;
import com.example.api.model.dto.UserForUpdateDto;
import com.example.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Spy
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserService userService;

    @Test
    public void createUser_userNotNull() {
        //Arrange
        var address = Address.builder().city("City").street("Street").building(1).apartment(1).build();
        var userForCreationDto = UserForCreationDto.builder().firstName("Firstname").lastName("Lastname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();
        var user = User.builder().firstName("Firstname").lastName("Lastname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        var userServiceSpy = Mockito.spy(userService);
        Mockito.doReturn(true).when(userServiceSpy).isUserValid(Mockito.any());

        //Act
        var userCreated = userServiceSpy.createUser(userForCreationDto);

        //Assert
        assertNotNull(userCreated);
    }

    @Test
    public void getUser_userNotNull() {
        //Arrange
        var user = new User();
        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(user));

        //Act
        var userGet = userService.getUser(UUID.fromString("7644f62c-61c6-40ef-b886-fa1ad056bd97"));

        //Assert
        assertNotNull(userGet);
    }

    @Test
    public void getAllUsers_usersSizeGreaterThanOne() {
        //Arrange
        var users = new ArrayList<User>();
        users.add(new User());
        users.add(new User());
        Mockito.when(userRepository.findAll()).thenReturn(users);

        //Act
        var usersGet = userService.getUsers(null, null);

        //Assert
        assertThat(usersGet.size()).isGreaterThan(1);
    }

    @Test
    public void getUsersByFirstName_firstUserOfListNotNull() {
        //Arrange
        var users = new ArrayList<User>();
        users.add(User.builder().firstName("Firstname").build());
        Mockito.when(userRepository.findByFirstName(Mockito.anyString())).thenReturn(users);

        //Act
        var usersGet = userService.getUsers("Firstname", null);

        //Assert
        assertNotNull(usersGet.getFirst());
    }

    @Test
    public void getUsersByLastName_firstUserOfListNotNull() {
        //Arrange
        var users = new ArrayList<User>();
        users.add(User.builder().lastName("Lastname").build());
        Mockito.when(userRepository.findByLastName(Mockito.anyString())).thenReturn(users);

        //Act
        var usersGet = userService.getUsers(null, "Lastname");

        //Assert
        assertNotNull(usersGet.getFirst());
    }

    @Test
    public void getUsersByFullName_firstUserOfListNotNull() {
        //Arrange
        var users = new ArrayList<User>();
        users.add(User.builder().firstName("Firstname").lastName("Lastname").build());
        Mockito.when(userRepository.findByFirstNameAndLastName(Mockito.anyString(), Mockito.anyString())).thenReturn(users);

        //Act
        var usersGet = userService.getUsers("Firstname", "Lastname");

        //Assert
        assertNotNull(usersGet.getFirst());
    }

    @Test
    public void addUser_userNotNull() {
        //Arrange
        var user = new User();
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        //Act
        var userAdded = userService.addUser(user);

        //Assert
        assertNotNull(userAdded);
    }

    @Test
    public void updateUser_firstNameIsCorrect() {
        //Arrange
        var address = Address.builder().city("City").street("Street").building(1).apartment(1).build();
        var userToBeUpdated = User.builder().userId(UUID.fromString("7644f62c-61c6-40ef-b886-fa1ad056bd97")).firstName("Firstnamebefore").lastName("Lastname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();
        var userForUpdateDto = UserForUpdateDto.builder().firstName("Firstnameafter").lastName("Lastname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();
        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(userToBeUpdated));
        var userServiceSpy = Mockito.spy(userService);
        Mockito.doReturn(true).when(userServiceSpy).isUserValid(Mockito.any(User.class));

        //Act
        var userUpdated = userServiceSpy.updateUser(userForUpdateDto, userToBeUpdated.getUserId());

        //Assert
        assertEquals("Firstnameafter", userUpdated.getFirstName());
    }

    @Test
    public void deleteUser_returnsUserDto() {
        //Arrange
        var user = new User();
        Mockito.when(userRepository.findById(Mockito.any(UUID.class))).thenReturn(Optional.of(user));

        //Act

        //Assert
        assertAll(() -> userService.deleteUser(UUID.fromString("7644f62c-61c6-40ef-b886-fa1ad056bd97")));
    }

    @Test
    public void isUserValid_noLastNameReturnsFalse() {
        //Arrange
        var address = Address.builder().city("City").street("Street").building(1).apartment(1).build();
        var user = User.builder().firstName("Firstname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();

        //Act
        var result = userService.isUserValid(user);

        //Assert
        assertFalse(result);
    }

    @Test
    public void isUserValid_returnsTrue() {
        //Arrange
        var address = Address.builder().city("City").street("Street").building(1).apartment(1).build();
        var user = User.builder().firstName("Firstname").lastName("Lastname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();
        var userServiceSpy = Mockito.spy(userService);
        Mockito.doReturn(true).when(userServiceSpy).isAddressValid(Mockito.any(Address.class));

        //Act
        var result = userServiceSpy.isUserValid(user);

        //Assert
        assertTrue(result);
    }

    @Test
    public void isAddressValid_noStreetReturnsFalse()
    {
        //Arrange
        var address = Address.builder().city("City").building(1).apartment(1).build();

        //Act
        var result = userService.isAddressValid(address);

        //Assert
        assertFalse(result);
    }

    @Test
    public void isAddressValid_returnsTrue()
    {
        //Arrange
        var address = Address.builder().city("City").street("Street").building(1).apartment(1).build();

        //Act
        var result = userService.isAddressValid(address);

        //Assert
        assertTrue(result);
    }
}