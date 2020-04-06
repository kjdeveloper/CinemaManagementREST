package com.app.model;

import com.app.model.base.BaseEntity;
import com.app.model.enums.CinemaHallType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "cinema_halls")
public class CinemaHall extends BaseEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private CinemaHallType type;

    @OneToOne(mappedBy = "cinemaHall")
    private FilmShow filmShow;

    @OneToMany(mappedBy = "cinemaHall")
    private Set<Place> places;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Override
    public String toString() {
        return "CinemaHall: " +
                "name: " + name +
                ", type: " + type;
    }
}
