package com.app.repository;

import com.app.model.FilmShow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface FilmShowRepository extends JpaRepository<FilmShow, Long> {
    Optional<FilmShow> findByStartTime_DateAndMovie_Title(LocalDate start, String title);
}
