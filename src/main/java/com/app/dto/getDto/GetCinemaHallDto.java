package com.app.dto.getDto;

import com.app.model.enums.CinemaHallType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCinemaHallDto {

    private Long id;
    private String name;
    private CinemaHallType cinemaHallType;
    private Set<GetPlaceDto> places;
    private GetFilmShowDto getFilmShowDto;
    private GetCinemaDto cinemaDto;

}
