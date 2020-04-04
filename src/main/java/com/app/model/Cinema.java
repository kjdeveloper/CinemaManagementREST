package com.app.model;

import com.app.model.base.BaseEntity;
import com.app.model.enums.City;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "cinemas")
public class Cinema extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany(mappedBy = "cinema")
    private Set<Ticket> tickets;

    @OneToMany(mappedBy = "cinema")
    private Set<Repertoire> repertoires;

    @OneToMany(mappedBy = "cinema")
    private Set<CinemaHall> cinemaHalls;

    @Override
    public String toString() {
        return "Cinema: " +
                "name: " + name +
                ", city: " + city;
    }
}
