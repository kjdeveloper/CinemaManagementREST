package com.app.dto.getDto;

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
public class GetCinemaDto {

    private Long id;
    private String name;
    private City city;
    private Long version;
    Set<GetRepertoireDto> repertoires;
}