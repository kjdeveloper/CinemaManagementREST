package com.app.dto.downloadingDto;

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
public class FilmShowDownloadDto {

    private Long id;
    private LocalDateTime startTime;
    private Integer cinemaHall;
    private Integer duration;
    private Integer ticketsAvailable;
    private MovieDownloadDto movie;
    Set<TicketDownloadDto> tickets;

}
