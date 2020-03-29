package com.app.dto.getDto;

import com.app.model.FilmShow;
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
public class GetHistoryTicketDto {

    private FilmShow filmShow;
    private BigDecimal price;
    private Long version;
    private TicketType ticketType;

    @Override
    public String toString() {
        return "FilmShow: " + filmShow +
                "\n, price: " + price +
                "\n, ticket type: " + ticketType +
                '\n';
    }
}
