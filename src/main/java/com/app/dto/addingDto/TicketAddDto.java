package com.app.dto.addingDto;

import com.app.model.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketAddDto {

    private LocalDateTime dateOfPurchase;
    private Boolean reservation;
    private BigDecimal price;
    TicketType ticketType;

    private UserAddDto user;
    private FilmShowAddDto filmShow;
}
