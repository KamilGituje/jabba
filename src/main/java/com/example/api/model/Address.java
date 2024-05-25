package com.example.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID addressId;
    private String city;
    private String street;
    private int building;
    private int apartment;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}