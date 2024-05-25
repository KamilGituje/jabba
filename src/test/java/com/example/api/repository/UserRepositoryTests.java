package com.example.api.repository;

import com.example.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void save_userNotNull()
    {
        //Arrange
        var user = User.builder().firstName("Firstname").lastName("Lastname").build();

        //Act
        var userSaved = userRepository.save(user);

        //Assert
        assertNotNull(userSaved);
    }

    @Test
    public void getById_userNotNull()
    {
        //Arrange
        var user = User.builder().firstName("Firstname").lastName("Lastname").build();
        var userSaved = userRepository.save(user);

        //Act
        var userGet = userRepository.findById(userSaved.getUserId());

        //Assert
        assertNotNull(userGet);
    }

    @Test
    public void getAll_usersCountGreaterThanOne()
    {
        //Arrange
        var user1 = User.builder().firstName("Firstname").lastName("Lastname").build();
        var user2 = User.builder().firstName("Firstname").lastName("Lastname").build();
        userRepository.save(user1);
        userRepository.save(user2);

        //Act
        var usersGet = userRepository.findAll();

        //Assert
        assertThat(usersGet.size()).isGreaterThan(1);
    }

    @Test
    public void getUsersByFirstName_firstUserOfListNotNull()
    {
        //Arrange
        var user = User.builder().firstName("Firstname").lastName("Lastname").build();
        userRepository.save(user);

        //Act
        var userGet = userRepository.findByFirstName("Firstname");

        //Assert
        assertNotNull(userGet.getFirst());
    }

    @Test
    public void getUsersByLastName_firstUserOfListNotNull()
    {
        //Arrange
        var user = User.builder().firstName("Firstname").lastName("Lastname").build();
        userRepository.save(user);

        //Act
        var userGet = userRepository.findByLastName("Lastname");

        //Assert
        assertNotNull(userGet.getFirst());
    }

    @Test
    public void getUsersByFullName_firstUserOfListNotNull()
    {
        //Arrange
        var user = User.builder().firstName("Firstname").lastName("Lastname").build();
        userRepository.save(user);

        //Act
        var userGet = userRepository.findByFirstNameAndLastName("Firstname", "Lastname");

        //Assert
        assertNotNull(userGet.getFirst());
    }

    @Test
    public void deleteUser_getAllUsersSizeIsZero()
    {
        //Arrange
        var user = new User();
        var userSaved = userRepository.save(user);

        //Act
        userRepository.delete(userSaved);

        //Assert
        var usersGet = userRepository.findAll();
        assertThat(usersGet.size()).isEqualTo(0);
    }
}
