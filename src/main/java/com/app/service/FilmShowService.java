package com.app.service;

import com.app.dto.createDto.CreateFilmShowDto;
import com.app.dto.getDto.GetFilmShowDto;
import com.app.exception.AppException;
import com.app.model.Cinema;
import com.app.model.FilmShow;
import com.app.model.Movie;
import com.app.repository.CinemaRepository;
import com.app.repository.FilmShowRepository;
import com.app.repository.MovieRepository;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FilmShowService {

    private final FilmShowRepository filmShowRepository;
    private final MovieRepository movieRepository;
    private final CinemaRepository cinemaRepository;

    public List<GetFilmShowDto> findAll() {
        return filmShowRepository.findAll()
                .stream()
                .map(GetMappers::fromFilmShowToGetFilmShowDto)
                .collect(Collectors.toList());
    }

    public List<GetFilmShowDto> findAllFilmShowsInParticularCinema(Long cinemaId) {
        if (cinemaId == null) {
            throw new AppException("id is null");
        }

        Cinema cinema = cinemaRepository.findById(cinemaId)
                .orElseThrow(() -> new AppException("Cannot find cinema with given id"));

        return cinema.getRepertoires()
                .stream()
                .flatMap(f -> f.getFilmShows()
                        .stream().map(GetMappers::fromFilmShowToGetFilmShowDto)).distinct().collect(Collectors.toList());
    }

    public List<GetFilmShowDto> findFilmShowsByMovieTitle(String movieTitle) {
        if (movieTitle == null) {
            throw new AppException("id is null");
        }


        return filmShowRepository
                .findAllByMovie_Title(movieTitle)
                .stream()
                .flatMap(Collection::stream)
                .map(GetMappers::fromFilmShowToGetFilmShowDto)
                .collect(Collectors.toList());
    }

    public Long add(Long cinemaId, CreateFilmShowDto createFilmShowDto) {
        if (createFilmShowDto == null) {
            throw new AppException("Film show is null");
        }
        if (cinemaRepository.findById(cinemaId).isPresent() &&
                filmShowRepository.findByStartTimeAndCinemaHall_Id(createFilmShowDto.getStartTime(), createFilmShowDto.getCinemaHall().getId()).isPresent()) {
            throw new AppException("Film show in this cinema with time " + createFilmShowDto.getStartTime() + " and cinema hall exist");
        }

        Movie movie = movieRepository.findByTitleAndDirector(createFilmShowDto.getMovie().getTitle(), createFilmShowDto.getMovie().getTitle())
                .orElseThrow(() -> new AppException("Cannot find movie with given title and director"));

        FilmShow filmShow = CreateMappers.fromCreateFilmShowDtoToFilmShow(createFilmShowDto);
        filmShow.setMovie(movie);
        filmShowRepository.save(filmShow);
        return filmShow.getId();
    }

    public Long edit(Long id, CreateFilmShowDto createFilmShowDto) {
        if (id == null) {
            throw new AppException("id is null");
        }
        if (createFilmShowDto == null) {
            throw new AppException("Film show is null");
        }

        FilmShow filmShow = filmShowRepository
                .findById(id)
                .orElseThrow(() -> new AppException("Film show with id " + id + " doesn't exist"));

        filmShow.setCinemaHall(createFilmShowDto.getCinemaHall() != null ? CreateMappers.fromCreateCinemaHallDtoToCinemaHall(createFilmShowDto.getCinemaHall()) : filmShow.getCinemaHall());
        filmShow.setStartTime(createFilmShowDto.getStartTime() != null ? createFilmShowDto.getStartTime() : filmShow.getStartTime());

        filmShowRepository.save(filmShow);
        return filmShow.getId();
    }

    public Long deleteById(Long id) {
        if (id == null) {
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
