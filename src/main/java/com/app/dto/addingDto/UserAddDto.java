package com.app.dto.addingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddDto {

    private String name;
    private String surname;
    private Integer age;
    private String email;
    private String password;
    private Set<String> role;
}
