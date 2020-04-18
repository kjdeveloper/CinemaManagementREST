package com.app.repository;

import com.app.model.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {

    List<CinemaHall> findCinemaHallsByCinema_Id(Long id);

    List<CinemaHall> findAllByName(String name);

    Optional<CinemaHall> findByNameAndCinema_Id(String name, Long id);
}
