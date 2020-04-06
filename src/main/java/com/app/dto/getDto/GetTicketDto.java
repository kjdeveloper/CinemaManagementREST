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
public class GetTicketDto {

    private Long id;
    private Long version;
    private BigDecimal price;
    private GetUserDto user;
    private GetFilmShowDto filmShow;
    private GetCinemaDto cinema;
    private GetPlaceDto place;
    private GetTicketTypeDto ticketTypeDto;

    @Override
    public String toString() {
        return "Ticket " +
                "price: " + getTicketTypeDto().getPrice() +
                ", user: " + user.getUsername() +
                ", movie: " + filmShow.getMovie().getTitle() +
                ", cinema: " + cinema.getCity() +
                ", row: " + place.getRow_num() + ", number: " + place.getNumber();
    }
}
