package com.app.dto.getDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetHistoryTicketDto {

    private String filmShow;
    private BigDecimal price;
    private GetTicketTypeDto ticketTypeDto;
}
