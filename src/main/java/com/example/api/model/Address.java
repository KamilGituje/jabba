package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {
    @Id
    private int addressId;
    private String city;
    private String street;
    private int building;
    private int apartment;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
}