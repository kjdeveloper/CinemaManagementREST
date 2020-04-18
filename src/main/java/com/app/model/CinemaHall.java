package com.app.model;

import com.app.model.base.BaseEntity;
import com.app.model.enums.CinemaHallType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "cinemaHall")
    private Set<FilmShow> filmShow;

    @OneToMany(mappedBy = "cinemaHall", fetch = FetchType.EAGER)
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
