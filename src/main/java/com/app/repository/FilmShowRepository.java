package com.app.repository;

import com.app.model.FilmShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmShowRepository extends JpaRepository<FilmShow, Long> {
    List<FilmShow> findAllByMovie_Title(String title);

    List<FilmShow> findAllByMovie_TitleAndMovie_Director(String title, String director);

    List<FilmShow> findByCinemaHall_Id(Long cinemaHallId);
}
