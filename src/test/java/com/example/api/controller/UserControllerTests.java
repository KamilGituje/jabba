package com.example.api.controller;

import com.example.api.model.Address;
import com.example.api.model.dto.UserDto;
import com.example.api.model.dto.UserForCreationDto;
import com.example.api.model.dto.UserForUpdateDto;
import com.example.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private UserDto userDto;
    private Address address;

    @BeforeEach
    public void init() {
        address = Address.builder().addressId(UUID.fromString("a1c8d426-0aeb-4981-969d-b0ed2bd92d63")).city("City").street("Street").building(1).apartment(1).build();
        userDto = UserDto.builder().userId(UUID.fromString("7644f62c-61c6-40ef-b886-fa1ad056bd97")).firstName("Firstname").lastName("Lastname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();
    }

    @Test
    public void getUser_responseStatusOkReturnsObject() throws Exception {
        //Arrange
        Mockito.when(userService.getUser(Mockito.any(UUID.class))).thenReturn(userDto);

        //Act
        var httpResponse = mockMvc.perform(get("/users/7644f62c-61c6-40ef-b886-fa1ad056bd97").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userDto)));

        //Assert
        httpResponse.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    public void getAllUsers_responseStatusOkReturnsList() throws Exception {
        //Arrange
        var users = new ArrayList<UserDto>();
        users.add(userDto);
        users.add(userDto);
        Mockito.when(userService.getUsers(null, null)).thenReturn(users);

        //Act
        var httpResponse = mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(users)));

        //Assert
        httpResponse.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void getUsersByFirstName_responseStatusOkReturnsObject() throws Exception {
        //Arrange
        var users = new ArrayList<UserDto>();
        users.add(userDto);
        Mockito.when(userService.getUsers("Firstname", null)).thenReturn(users);

        //Act
        var httpResponse = mockMvc.perform(get("/users").param("firstName", "Firstname").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(users)));

        //Assert
        httpResponse.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void getUsersByLastName_responseStatusOkReturnsObject() throws Exception {
        //Arrange
        var users = new ArrayList<UserDto>();
        users.add(userDto);
        Mockito.when(userService.getUsers(null, "Lastname")).thenReturn(users);

        //Act
        var httpResponse = mockMvc.perform(get("/users").param("lastName", "Lastname").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(users)));

        //Assert
        httpResponse.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void getUsersByFullName_responseStatusOkReturnsObject() throws Exception
    {
        //Arrange
        var users = new ArrayList<UserDto>();
        users.add(userDto);
        Mockito.when(userService.getUsers("Firstname", "Lastname")).thenReturn(users);

        //Act
        var httpResponse = mockMvc.perform(get("/users").param("firstName", "Firstname").param("lastName", "Lastname").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(users)));

        //Assert
        httpResponse.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(users)));
    }

    @Test
    public void createUser_responseStatusCreatedReturnsObject() throws Exception
    {
        //Arrange
        var address = Address.builder().city("City").street("Street").building(1).apartment(1).build();
        var userForCreationDto = UserForCreationDto.builder().firstName("Firstname").lastName("Lastname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();
        Mockito.when(userService.createUser(Mockito.any(UserForCreationDto.class))).thenReturn(userDto);

        //Act
        var httpResponse = mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userForCreationDto)));
        
        //Assert
        httpResponse.andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    public void updateUser_responseStatusOkReturnsObject() throws Exception
    {
        //Arrange
        var address = Address.builder().city("City").street("Street").building(1).apartment(1).build();
        var userForUpdateDto = UserForUpdateDto.builder().firstName("Firstname").lastName("Lastname").birthDate(LocalDate.parse("2000-01-01")).address(address).email("email").build();
        Mockito.when(userService.updateUser(Mockito.any(UserForUpdateDto.class), Mockito.any(UUID.class))).thenReturn(userDto);

        //Act
        var httpResponse = mockMvc.perform(put("/users/{userId}", userDto.getUserId()).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(userForUpdateDto)));

        //Assert
        httpResponse.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(userDto)));
    }

    @Test
    public void deleteUser_responseStatusOk() throws Exception
    {
        //Act
        var httpResponse = mockMvc.perform(delete("/users/{userId}", userDto.getUserId()));

        //Assert
        httpResponse.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}