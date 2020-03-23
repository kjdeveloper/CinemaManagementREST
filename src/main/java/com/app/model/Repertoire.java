package com.app.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "repertoires")
public class Repertoire {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDate date;

    @OneToMany(mappedBy = "repertoire")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<FilmShow> filmShows = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

}
