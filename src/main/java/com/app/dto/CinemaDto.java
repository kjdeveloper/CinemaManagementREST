package com.app.dto;

import com.app.model.enums.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CinemaDto {

    private Long id;
    private String name;
    private City city;
    private Set<RepertoireDto> repertoiresDto;

}
