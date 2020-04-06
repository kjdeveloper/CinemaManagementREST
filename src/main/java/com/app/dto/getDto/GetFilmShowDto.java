package com.app.dto.getDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetFilmShowDto {

    private Long id;
    private Long version;
    private LocalDateTime startTime;
    private Integer ticketsAvailable;
    private GetMovieDto movie;
    private GetCinemaHallDto cinemaHall;

    @Override
    public String toString() {
        return "Film show : " +
                ", start time: " + startTime +
                ", tickets available: " + ticketsAvailable +
                ", movie: " + movie +
                ", cinemaHall: " + cinemaHall.getName();
    }
}
