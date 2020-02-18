package com.app.dto.getDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCinemaHallDto {

    private Long id;
    private Integer row;
    private Integer place;
    private GetFilmShowDto getFilmShowDto;

}
