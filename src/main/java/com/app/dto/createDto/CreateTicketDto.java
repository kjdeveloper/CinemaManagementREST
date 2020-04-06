package com.app.dto.createDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketDto {

    private Long ticketTypeId;
    private Long placeId;
    private Long userId;
    private Long filmShowId;
}
