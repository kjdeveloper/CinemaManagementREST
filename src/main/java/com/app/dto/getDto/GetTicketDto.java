package com.app.dto.getDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTicketDto {

    private Long id;
    private Long version;
    private LocalDate dateOfPurchase;
    private BigDecimal price;
    private GetUserDto user;
    private GetFilmShowDto filmShow;
    private GetCinemaDto cinema;
    private Set<GetPlaceDto> places;
    private GetTicketTypeDto ticketTypeDto;

    @Override
    public String toString() {
        return "Ticket(s): " +
                ", date of purchase: " + dateOfPurchase +
                ", price: " + price +
                ", user: " + user.getUsername() +
                ", filmShow: " + filmShow.getMovie() +
                ", time: " + filmShow.getStartTime() +
                ", cinema: " + cinema.getName() +
                " in " + cinema.getCity() +
                ", places: " + places +
                ", ticket type: " + ticketTypeDto;
    }
}
