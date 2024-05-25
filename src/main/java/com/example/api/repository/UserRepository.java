package com.example.api.repository;

import com.example.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    List<User> findByFirstNameAndLastName(String firstName, String lastName);

    List<User> findByFirstName(String firstName);

    List<User> findByLastName(String lastName);
}
