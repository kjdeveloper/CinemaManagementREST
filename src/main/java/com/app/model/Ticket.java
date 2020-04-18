package com.app.model;

import com.app.model.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    private Boolean reservation;
    private LocalDate dateOfPurchase;
    private Long placeId;
    private BigDecimal price;

    @OneToMany(mappedBy = "ticket", fetch = FetchType.EAGER)
    private Set<Place> places;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ticket_type_id")
    private TicketType ticketType;

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
                ", places: " + places +
                ", film show: " + filmShow.getMovie() +
                ", film show time: " + filmShow.getStartTime() +
                ", cinema: " + cinema.getName() +
                ", user: " + user.getUsername();
    }
}

