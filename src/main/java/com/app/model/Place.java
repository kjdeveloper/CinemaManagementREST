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
    private Integer rowNum;
    private Integer number;

    @OneToOne(mappedBy = "place")
    private Ticket ticket;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinema_hall_id")
    private CinemaHall cinemaHall;

    @Override
    public String toString() {
        return "Place: " +
                "available: " + available +
                ", row: " + rowNum +
                ", number: " + number +
                ", cinema hall: " + cinemaHall.getName();
    }
}

