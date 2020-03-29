package com.app.service;

import com.app.dto.createDto.CreateCinemaDto;
import com.app.dto.getDto.GetCinemaDto;
import com.app.exception.AppException;
import com.app.model.Cinema;
import com.app.repository.CinemaHallRepository;
import com.app.repository.CinemaRepository;
import com.app.repository.RepertoireRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final RepertoireRepository repertoireRepository;

    public List<GetCinemaDto> findAll() {
        return cinemaRepository.findAll()
                .stream()
                .map(GetMappers::fromCinemaToGetCinemaDto)
                .collect(Collectors.toList());
    }

    public GetCinemaDto findById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        return cinemaRepository
                .findById(id)
                .map(GetMappers::fromCinemaToGetCinemaDto)
                .orElseThrow(() -> new AppException("Cinema with id " + id + " doesn't exist"));
    }

    public Long add(CreateCinemaDto createCinemaDto) {
        if (Objects.isNull(createCinemaDto)) {
            throw new AppException("Cinema is null");
        }

        if (cinemaRepository.findByName(createCinemaDto.getName()).isPresent()) {
            throw new AppException("Cinema with name "
                    + createCinemaDto.getName() + " in city "
                    + createCinemaDto.getCity() + " already exist");
        }

        Cinema cinema = CreateMappers.fromCreateCinemaDtoToCinema(createCinemaDto);
        cinemaRepository.save(cinema);
        return cinema.getId();
    }

    public Long update(Long id, CreateCinemaDto createCinemaDto) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }
        if (Objects.isNull(createCinemaDto)) {
            throw new AppException("Cinema is null");
        }

        Cinema cinema = cinemaRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Cinema with id " + id + " doesn't exist"));

        cinema.setCity(createCinemaDto.getCity() != null ? createCinemaDto.getCity() : cinema.getCity());
        cinema.setName(createCinemaDto.getName() != null ? createCinemaDto.getName() : cinema.getName());

        cinemaRepository.save(cinema);
        return cinema.getId();
    }

    public Long deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        Cinema cinema = cinemaRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Cinema with id " + id + " doesn't exist"));

        repertoireRepository.findByCinema_Id(id)
                .forEach(r -> r.setCinema(null));

        cinemaHallRepository.findCinemaHallByCinema_Id(id)
                .forEach(c -> c.setCinema(null));

        cinemaRepository.delete(cinema);
        return cinema.getId();
    }

    public Long deleteAll() {
        long rows = cinemaRepository.count();

        cinemaHallRepository.findAll()
                .forEach(c -> c.setCinema(null));

        repertoireRepository.findAll()
                .forEach(r -> r.setCinema(null));

        cinemaRepository.deleteAll();
        return rows;
    }


}
