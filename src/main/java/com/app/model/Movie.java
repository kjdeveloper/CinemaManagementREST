package com.app.model;

import com.app.model.base.BaseEntity;
import com.app.model.enums.Genre;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder

@Entity
@Table(name = "movies")
public class Movie extends BaseEntity {

    private String title;
    private String description;
    private String director;
    private Integer duration;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "movie")
    private Set<FilmShow> filmShows;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Movie:" +
                "title: " + title +
                ", description: " + description +
                ", director: " + director +
                ", duration: " + duration +
                ", genre: " + genre;
    }
}
