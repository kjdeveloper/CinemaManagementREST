package com.app.model;

import com.app.model.enums.City;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cinemas")
public class Cinema {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "cinema")
    private Set<Ticket> ticketSold = new HashSet<>();

    private String name;

    @Enumerated(EnumType.STRING)
    private City city;

    @OneToMany(mappedBy = "cinema")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Repertoire> repertoires = new HashSet<>();

    @OneToMany(mappedBy = "cinema")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CinemaHall> cinemaHalls = new HashSet<>();
}
