package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreatePlaceDto {

    private Long cinemaHallId;
    private Boolean available;
    private Integer row;
    private Integer number;
}
