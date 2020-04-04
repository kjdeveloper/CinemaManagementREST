package com.app.repository;

import com.app.model.CinemaHall;
import com.app.model.FilmShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FilmShowRepository extends JpaRepository<FilmShow, Long> {

    Optional<FilmShow> findByStartTimeAndCinemaHall_Id(LocalDateTime start, Long cinemaHallId);

    List<FilmShow> findAllByMovie_Title(String title);

    Set<FilmShow> findAllByMovie_Id(Long id);

    List<FilmShow> findAllByMovie_TitleAndMovie_Director(String title, String director);

    List<FilmShow> findByCinemaHall_Id(Long cinemaHallId);
}
