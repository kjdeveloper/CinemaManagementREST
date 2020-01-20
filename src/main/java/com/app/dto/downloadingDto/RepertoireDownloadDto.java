package com.app.dto.downloadingDto;

import com.app.dto.addingDto.CinemaAddDto;
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
public class RepertoireDownloadDto {

    private Long id;
    private LocalDate date;
    private CinemaAddDto cinemaAddDto;
    private Set<FilmShowDownloadDto> filmShows;
}
