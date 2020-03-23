package com.app.repository;

import com.app.model.Movie;
import com.app.model.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    List<Movie> findByTitle(String title);

    List<Movie> findByGenre(Genre genre);

    Optional<Movie> findByTitleAndDirector(String title, String director);
}
