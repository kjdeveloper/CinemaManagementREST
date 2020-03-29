package com.app.repository;

import com.app.model.Repertoire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface RepertoireRepository extends JpaRepository<Repertoire, Long> {
    Optional<List<Repertoire>> findByDateAndCinema_Id(LocalDate date, Long cinemaId);

    List<Repertoire> findByCinema_Id(long id);

    List<Repertoire> findByDateBetween(LocalDate from, LocalDate to);

    List<Repertoire> findRepertoireByCinema_Id(Long id);
}

