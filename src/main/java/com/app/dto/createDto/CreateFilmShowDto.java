package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFilmShowDto {

    private LocalDateTime startTime;
    private CreateCinemaHallDto cinemaHall;
    private CreateMovieDto movie;
    private Integer ticketsAvailable;

}
