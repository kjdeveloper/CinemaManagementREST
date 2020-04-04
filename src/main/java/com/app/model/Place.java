package com.app.model;

import com.app.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "places")
public class Place extends BaseEntity {

    private Boolean available;

    @Column(name = "row_num")
    private Integer row;
    private Integer number;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "place")
    private Ticket ticket;

    @Override
    public String toString() {
        return "Place: " +
                "available: " + available +
                ", row: " + row +
                ", number: " + number +
                ", cinema hall: " + cinemaHall.getName();
    }
}

