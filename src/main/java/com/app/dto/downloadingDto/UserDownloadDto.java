package com.app.dto.downloadingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDownloadDto {

    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String email;
    private Set<String> role;
}
