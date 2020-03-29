package com.app.dto.createDto;

import com.app.model.enums.City;
import com.app.model.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketDto {

    private Long id;
    private TicketType ticketType;
    private BigDecimal price;
    private Long placeId;
    private Long cinemaId;
    private Long userId;
    private Long filmShowId;
}
