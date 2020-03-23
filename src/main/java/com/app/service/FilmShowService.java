package com.app.service;

import com.app.dto.createDto.CreateFilmShowDto;
import com.app.dto.getDto.GetFilmShowDto;
import com.app.exception.AppException;
import com.app.model.*;
import com.app.repository.*;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmShowService {

    private final FilmShowRepository filmShowRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;
    private final CinemaHallRepository cinemaHallRepository;
    private final RepertoireRepository repertoireRepository;

    public List<GetFilmShowDto> findAll() {
        return filmShowRepository.findAll()
                .stream()
                .map(GetMappers::fromFilmShowToGetFilmShowDto)
                .collect(Collectors.toList());
    }

    public List<GetFilmShowDto> findAllFilmShowsInParticularCinema(Long cinemaId) {
        if (Objects.isNull(cinemaId)) {
            throw new AppException("id is null");
        }

        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AppException("Cannot find cinema with given id"));

        return cinema.getRepertoires()
                .stream()
                .flatMap(f -> f.getFilmShows()
                        .stream().map(GetMappers::fromFilmShowToGetFilmShowDto)).collect(Collectors.toList());
    }

    public List<GetFilmShowDto> findFilmShowsByMovieTitle(String movieTitle) {
        if (Objects.isNull(movieTitle)) {
            throw new AppException("id is null");
        }

        return filmShowRepository
                .findAllByMovie_Title(movieTitle)
                .stream()
                .map(GetMappers::fromFilmShowToGetFilmShowDto)
                .collect(Collectors.toList());
    }

    public Long add(CreateFilmShowDto createFilmShowDto) {
        if (Objects.isNull(createFilmShowDto)) {
            throw new AppException("Film show is null");
        }

        Repertoire repertoire = repertoireRepository.findById(createFilmShowDto.getRepertoireId())
                .orElseThrow(() -> new AppException("Repertoire with given id doesn't exist"));

        CinemaHall cinemaHall = cinemaHallRepository.findById(createFilmShowDto.getCinemaHallId())
                .orElseThrow(() -> new AppException("Cinema has not cinema hall with given id"));

        Movie movie = movieRepository.findById(createFilmShowDto.getMovieId())
                .orElseThrow(() -> new AppException("Cannot find movie with given title and director"));

        FilmShow filmShow = CreateMappers.fromCreateFilmShowDtoToFilmShow(createFilmShowDto);

        filmShow.setRepertoire(repertoire);
        filmShow.setMovie(movie);
        filmShow.setCinemaHall(cinemaHall);

        filmShowRepository.save(filmShow);
        return filmShow.getId();
    }

    public Long edit(Long id, CreateFilmShowDto createFilmShowDto) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }
        if (Objects.isNull(createFilmShowDto)) {
            throw new AppException("Film show is null");
        }
        Movie movie = movieRepository.findById(createFilmShowDto.getMovieId())
                .orElse(null);

        CinemaHall cinemaHall = cinemaHallRepository.findById(createFilmShowDto.getCinemaHallId())
                .orElse(null);

        FilmShow filmShow = filmShowRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Film show with id " + id + " doesn't exist"));

        filmShow.setStartTime(createFilmShowDto.getStartTime() != null ? createFilmShowDto.getStartTime() : filmShow.getStartTime());
        filmShow.setMovie(movie != null ? movie : filmShow.getMovie());
        filmShow.setCinemaHall(cinemaHall != null ? cinemaHall : filmShow.getCinemaHall());

        filmShowRepository.save(filmShow);
        return filmShow.getId();
    }

    public Long deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("id is null");
        }
        FilmShow filmShow = filmShowRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Film show with id " + id + " doesn't exist"));

        filmShowRepository.delete(filmShow);
        return filmShow.getId();
    }

    public Long deleteAll() {
        long rows = filmShowRepository.count();
        filmShowRepository.deleteAll();
        return rows;
    }

}
