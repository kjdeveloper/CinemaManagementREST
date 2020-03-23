package com.app.repository;

import com.app.model.Repertoire;
import com.app.model.enums.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {
    Optional<List<Repertoire>> findByDateAndCinema_Id(LocalDate date, Long cinemaId);

    List<Repertoire> findByCinema_Id(long id);

    List<Repertoire> findByDateBetween(LocalDate from, LocalDate to);
}

