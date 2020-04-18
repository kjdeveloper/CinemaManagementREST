package com.app.dto.getDto;

import com.app.model.enums.CinemaHallType;
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
    private Long version;
    private String name;
    private CinemaHallType cinemaHallType;
    private GetCinemaDto cinemaDto;
    private Integer places;

}
