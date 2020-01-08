package com.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmShowDto {

    private Long id;
    private LocalDateTime startTime;
    private Integer cinemaHall;
    private Integer duration;
    private Integer ticketsAvailable;

    private RepertoireDto repertoireDto;
    private MovieDto movieDto;
}
