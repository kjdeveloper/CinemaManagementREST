package com.app.dto.getDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private Long id;
    private Long version;
    private String name;
    private String username;
    private Integer age;
    private String email;
    private Set<String> role;

}
