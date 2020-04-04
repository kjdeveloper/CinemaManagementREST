package com.app.model;

import com.app.model.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "repertoires")
public class Repertoire extends BaseEntity {

    private LocalDate date;

    @OneToMany(mappedBy = "repertoire")
    private Set<FilmShow> filmShows;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @Override
    public String toString() {
        return "Repertoire: " +
                "date: " + date +
                ", film shows: " + filmShows +
                ", cinema: " + cinema.getName();
    }
}
