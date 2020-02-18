package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String username;

    private String password;
    private String passwordConfirmation;

    private Set<String> roles;
}
