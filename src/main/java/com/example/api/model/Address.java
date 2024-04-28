package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "addresses")
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressId;
    private String city;
    private String street;
    private int building;
    private int apartment;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}