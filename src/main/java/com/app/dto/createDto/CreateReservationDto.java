package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReservationDto {

    private Long userId;
    private Long filmShowId;
    private Set<Long> placesId;
}
