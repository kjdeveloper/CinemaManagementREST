package com.app.dto.createDto;

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
public class CreateRepertoireDto {

    private LocalDate date;
    private Long cinemaId;
    private Set<CreateFilmShowDto> filmShows;
}
