package com.app.model;

import com.app.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    private Boolean reservation;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "filmShow_id")
    private FilmShow filmShow;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Ticket: " +
                ", ticketType: " + ticketType +
                "price: " + ticketType.getPrice() +
                ", place: row: " + place.getRow() +
                ", place: number: " + place.getNumber() +
                ", film show: " + filmShow.getMovie() +
                ", film show time: " + filmShow.getStartTime() +
                ", cinema: " + cinema.getName() +
                ", user: " + user.getUsername();
    }
}

