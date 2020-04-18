package com.app.service;

import com.app.dto.createDto.CreateCinemaHallDto;
import com.app.dto.getDto.GetCinemaHallDto;
import com.app.exception.AppException;
import com.app.model.Cinema;
import com.app.model.CinemaHall;
import com.app.model.Place;
import com.app.model.enums.CinemaHallType;
import com.app.repository.CinemaHallRepository;
import com.app.repository.CinemaRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaHallService {

    private final CinemaHallRepository cinemaHallRepository;
    private final CinemaRepository cinemaRepository;

    private final PlaceService placeService;

    public List<CinemaHallType> findAllCinemaHallTypes() {
        return Arrays
                .stream(CinemaHallType.values())
                .collect(Collectors.toList());
    }

    public List<GetCinemaHallDto> findAll() {
        return cinemaHallRepository.findAll()
                .stream()
                .map(GetMappers::fromCinemaHallToGetCinemaHallDto)
                .collect(Collectors.toList());
    }

    public List<GetCinemaHallDto> getAllHallsInParticularCinema(Long cinemaId) {

        if (Objects.isNull(cinemaId)) {
            throw new AppException("Cinema id is null");
        }

        return cinemaHallRepository
                .findCinemaHallsByCinema_Id(cinemaId)
                .stream()
                .map(GetMappers::fromCinemaHallToGetCinemaHallDto)
                .collect(Collectors.toList());
    }

    public List<GetCinemaHallDto> getByName(String name) {
        if (Objects.isNull(name)) {
            throw new AppException("Name is null");
        }

        return cinemaHallRepository.findAllByName(name)
                .stream()
                .map(GetMappers::fromCinemaHallToGetCinemaHallDto)
                .collect(Collectors.toList());
    }


    public Long add(Long cinemaId, CreateCinemaHallDto cinemaHallDto) {
        if (Objects.isNull(cinemaHallDto)) {
            throw new AppException("Cinema hall is null");
        }
        if (Objects.isNull(cinemaId)) {
            throw new AppException("Cinema name is null");
        }
        if (cinemaHallRepository.findByNameAndCinema_Id(cinemaHallDto.getName(), cinemaId).isPresent()) {
            throw new AppException("Cinema hall in this cinema id and given name already exist");
        }
        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AppException("Cinema with given id doesn't exist"));

        CinemaHall cinemaHall = CreateMappers.fromCreateCinemaHallDtoToCinemaHall(cinemaHallDto);
        Set<Place> places = placeService.createPlacesForHalls(cinemaHall, cinemaHallDto.getCinemaHallType().getRowNum(), cinemaHallDto.getCinemaHallType().getNumber());

        cinemaHall.setPlaces(places);
        cinemaHall.setCinema(cinema);
        cinemaHallRepository.save(cinemaHall);

        return cinemaHall.getId();
    }


    public Long update(Long id, CreateCinemaHallDto cinemaHallDto) {
        if (Objects.isNull(id)) {
            throw new AppException("Id is null");
        }

        if (Objects.isNull(cinemaHallDto)) {
            throw new AppException("Cinema hall is null");
        }

        CinemaHall cinemaHall = cinemaHallRepository.findById(id)
                .orElseThrow(() -> new AppException("Cinema hall with given id doesn't exist"));

        cinemaHall.setName(cinemaHallDto.getName() != null ? cinemaHallDto.getName() : cinemaHall.getName());
        cinemaHall.setType(cinemaHallDto.getCinemaHallType() != null ? cinemaHallDto.getCinemaHallType() : cinemaHall.getType());

        return cinemaHallRepository.save(cinemaHall).getId();
    }

    public Long deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }

        CinemaHall cinemaHall = cinemaHallRepository.findById(id)
                .orElseThrow(() -> new AppException("cinema hall with given id doesn't exist"));

        cinemaHallRepository.delete(cinemaHall);
        return cinemaHall.getId();
    }

    public Long deleteAll() {
        long rows = cinemaHallRepository.count();

        cinemaHallRepository.deleteAll();
        return rows;
    }
}
