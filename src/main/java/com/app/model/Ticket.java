package com.app.model;

import com.app.model.base.BaseEntity;
import com.app.model.enums.TicketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
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
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ticket_id")
    private Reservation reservation;
}

