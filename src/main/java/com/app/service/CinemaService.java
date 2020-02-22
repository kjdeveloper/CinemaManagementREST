package com.app.service;

import com.app.dto.createDto.CreateCinemaDto;
import com.app.dto.getDto.GetCinemaDto;
import com.app.exception.AppException;
import com.app.model.Cinema;
import com.app.repository.CinemaRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaService {

    private final CinemaRepository cinemaRepository;

    public List<GetCinemaDto> findAll() {
        return cinemaRepository.findAll()
                .stream()
                .map(GetMappers::fromCinemaToGetCinemaDto)
                .collect(Collectors.toList());
    }

    public GetCinemaDto findById(Long id) {
        if (id == null) {
            throw new AppException("id is null");
        }

        return cinemaRepository
                .findById(id)
                .map(GetMappers::fromCinemaToGetCinemaDto)
                .orElseThrow(() -> new AppException("Cinema with id " + id + " doesn't exist"));
    }

    public Long add(CreateCinemaDto createCinemaDto) {
        if (createCinemaDto == null) {
            throw new AppException("Cinema is null");
        }

        if (cinemaRepository.findByCity(createCinemaDto.getCity()).isPresent()) {
            throw new AppException("Cinema with name "
                    + createCinemaDto.getName() + " in city "
                    + createCinemaDto.getCity() + " already exist");
        }

        Cinema cinema = CreateMappers.fromCreateCinemaDtoToCinema(createCinemaDto);
        cinemaRepository.save(cinema);
        return cinema.getId();
    }

    public Long update(Long id, CreateCinemaDto createCinemaDto) {
        if (id == null) {
            throw new AppException("id is null");
        }
        if (createCinemaDto == null) {
            throw new AppException("Cinema is null");
        }

        Cinema cinema = cinemaRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Cinema with id " + id + " doesn't exist"));

        cinema.setCity(createCinemaDto.getCity() != null ? createCinemaDto.getCity() : cinema.getCity());

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
