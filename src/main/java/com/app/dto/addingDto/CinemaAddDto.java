package com.app.dto.addingDto;

import com.app.model.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaAddDto {

    private String name;
    private City city;
}
