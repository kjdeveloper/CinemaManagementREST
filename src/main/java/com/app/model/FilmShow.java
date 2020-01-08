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
    private Integer cinemaHall;
    private Integer duration;
    private Integer ticketsAvailable;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "repertoire_id")
    private Repertoire repertoire;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "filmShow_tickets", joinColumns = @JoinColumn(name = "filmShow_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id"))
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ticket> tickets = new HashSet<>();

}
