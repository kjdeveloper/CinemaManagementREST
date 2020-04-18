package com.app.dto.getDto;

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
public class GetRepertoireDto {

    private Long id;
    private Long version;
    private LocalDate date;
    private GetCinemaDto cinemaDto;
    private Set<GetFilmShowDto> filmShowDto;
}
