package com.app.dto.createDto;

import com.app.model.enums.City;
import com.app.model.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTicketDto {

    private Long id;
    private LocalDate date;
    private Integer quantity;
    private TicketType ticketType;
    private City city;
    private Long userId;
    private Long movieId;
    private LocalDateTime startTime;


}
