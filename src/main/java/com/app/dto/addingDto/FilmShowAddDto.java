package com.app.dto.addingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmShowAddDto {

    private LocalDateTime startTime;
    private Integer cinemaHall;
    private Integer duration;
    private MovieAddDto movie;
}
