package com.app.repository;

import com.app.model.Cinema;
import com.app.model.enums.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Optional<Cinema> findByName(String name);
}
