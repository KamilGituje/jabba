package com.example.api.model.dto;

import com.example.api.model.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserForCreationDto {
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthDate;
    private Address address;
    private String email;
}