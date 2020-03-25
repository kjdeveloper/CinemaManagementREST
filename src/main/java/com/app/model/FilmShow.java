package com.app.model;

import com.app.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "film_shows")
public class FilmShow extends BaseEntity {

    private LocalDateTime startTime;
    private Integer ticketsAvailable;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinemaHall_id")
    private CinemaHall cinemaHall;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "repertoire_id")
    private Repertoire repertoire;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "filmShow", fetch = FetchType.EAGER)
    private Set<Ticket> tickets;
}
