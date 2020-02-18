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
    private LocalDateTime startTime;
    private GetCinemaHallDto cinemaHall;
    private Integer ticketsAvailable;
    private GetMovieDto movie;
    Set<GetTicketDto> tickets;

}
