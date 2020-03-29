package com.app.dto.getDto;

import com.app.model.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTicketDto {

    private Long id;
    private Long version;
    private BigDecimal price;
    private GetUserDto user;
    private GetFilmShowDto filmShow;
    private GetCinemaDto cinema;
    private GetPlaceDto place;
    private TicketType ticketType;
}
