package com.app.dto.addingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RepertoireAddDto {

    private LocalDate date;
    private CinemaAddDto cinema;
    private Set<FilmShowAddDto> filmShows;
}
