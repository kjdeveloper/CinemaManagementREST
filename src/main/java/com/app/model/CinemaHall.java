package com.app.model;

import com.app.model.enums.CinemaHallType;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cinemaHalls")
public class CinemaHall {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CinemaHallType type;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

/*    @OneToMany(mappedBy = "cinemaHall", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Place> places = new HashSet<>();*/

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "filmShow_id")
    private FilmShow filmShow;
}
