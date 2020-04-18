package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateReservationDto {

    private Long filmShowId;
    private Long placeId;
    private Long ticketTypeId;
}
