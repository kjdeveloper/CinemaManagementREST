package com.app.service;

import com.app.dto.CinemaDto;
import com.app.exception.AppException;
import com.app.model.Cinema;
import com.app.repository.CinemaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    public List<CinemaDto> findAll() {
        return cinemaRepository.findAll()
                .stream()
                .map(Mappers::fromCinemaToCinemaDto)
                .collect(Collectors.toList());
    }

    public CinemaDto findById(Long id) {
        if (id == null) {
            throw new AppException("id is null");
        }

        return cinemaRepository
                .findById(id)
                .map(Mappers::fromCinemaToCinemaDto)
                .orElseThrow(() -> new AppException("Cinema with id " + id + " doesn't exist"));
    }

    public Long add(CinemaDto cinemaDto) {
        if (cinemaDto == null) {
            throw new AppException("Cinema is null");
        }

        if (cinemaRepository.findByNameAndCity(cinemaDto.getName(), cinemaDto.getCity()).isPresent()) {
            throw new AppException("Cinema with name "
                    + cinemaDto.getName() + " in city "
                    + cinemaDto.getCity() + " already exist");
        }

        Cinema cinema = Mappers.fromCinemaDtoToCinema(cinemaDto);
        cinemaRepository.save(cinema);
        return cinema.getId();
    }

    public Long update(Long id, CinemaDto cinemaDto) {
        if (id == null) {
            throw new AppException("id is null");
        }
        if (cinemaDto == null) {
            throw new AppException("Cinema is null");
        }

        Cinema cinema = cinemaRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Cinema with id " + id + " doesn't exist"));

        cinema.setName(cinemaDto.getName());
        cinema.setCity(cinemaDto.getCity());

        cinemaRepository.save(cinema);
        return cinema.getId();
    }

    public Long deleteById(Long id) {
        if (id == null) {
            throw new AppException("id is null");
        }

        Cinema cinema = cinemaRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Cinema with id " + id + " doesn't exist"));

        cinemaRepository.delete(cinema);
        return cinema.getId();
    }

    public Long deleteAll() {
        long rows = cinemaRepository.count();

        cinemaRepository.deleteAll();
        return rows;
    }


}
