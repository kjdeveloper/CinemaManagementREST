package com.app.repository;

import com.app.model.FilmShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FilmShowRepository extends JpaRepository<FilmShow, Long> {

    Optional<FilmShow> findByStartTimeAndCinemaHall_Id(LocalDateTime start, Long cinemaHallId);

    List<FilmShow> findAllByMovie_Title(String title);

    List<FilmShow> findAllByMovie_TitleAndMovie_Director(String title, String director);
}
