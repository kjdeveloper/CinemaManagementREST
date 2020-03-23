package com.app.dto.createDto;

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
public class CreateFilmShowDto {

    private LocalDateTime startTime;
    private Long cinemaId;
    private Long cinemaHallId;
    private Long movieId;
    private Integer ticketsAvailable;
}
