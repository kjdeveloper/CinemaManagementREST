package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCinemaHallDto {

    private Long id;
    private Integer row;
    private Integer place;
    private CreateFilmShowDto createFilmShowDto;

}
