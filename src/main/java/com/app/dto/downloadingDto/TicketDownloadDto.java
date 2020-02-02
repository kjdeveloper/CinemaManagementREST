package com.app.dto.downloadingDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketDownloadDto {

    private Long id;
    private Boolean reservation;
    private UserDownloadDto user;
    private FilmShowDownloadDto filmShow;
}
