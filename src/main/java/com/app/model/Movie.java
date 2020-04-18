package com.app.model;

import com.app.model.base.BaseEntity;
import com.app.model.enums.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    @ManyToMany(mappedBy = "favouriteMovies")
    private Set<User> user;

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
