package com.app.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "filmShows")
public class FilmShow {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime startTime;
    private Integer ticketsAvailable;

    @OneToOne(cascade = CascadeType.PERSIST, mappedBy = "filmShow")
    private CinemaHall cinemaHall;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "repertoire_id")
    private Repertoire repertoire;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "filmShow", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ticket> tickets = new HashSet<>();

}
