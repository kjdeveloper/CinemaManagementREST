package com.app.dto.createDto;

import com.app.model.enums.CinemaHallType;
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
    private String name;
    private CinemaHallType cinemaHallType;
    private CreateFilmShowDto createFilmShowDto;

}
