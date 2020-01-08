package com.app.model;

import com.app.model.enums.Genre;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String description;
    private String director;
    private int duration;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "movie")
    private Set<FilmShow> filmShows = new HashSet<>();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
}
