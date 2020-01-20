package com.app.service.mappers;

import com.app.dto.addingDto.*;
import com.app.model.*;

import java.util.HashSet;

public interface AddMappers {

    static MovieAddDto fromMovieToMovieAddDto(Movie movie){
        return movie == null ? null : MovieAddDto.builder()
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .build();
    }

    static Movie fromMovieAddDtoToMovie(MovieAddDto movieAddDto){
        return movieAddDto == null ? null : Movie.builder()
                .title(movieAddDto.getTitle())
                .description(movieAddDto.getDescription())
                .director(movieAddDto.getDirector())
                .duration(movieAddDto.getDuration())
                .genre(movieAddDto.getGenre())
                .build();
    }

    static Cinema fromCinemaAddDtoToCinema(CinemaAddDto cinemaAddDto){
        return cinemaAddDto == null ? null : Cinema.builder()
                .name(cinemaAddDto.getName())
                .city(cinemaAddDto.getCity())
                .repertoires(new HashSet<>())
                .build();
    }

    static CinemaAddDto fromCinemaToCinemaAddDto(Cinema cinema){
        return cinema == null ? null : CinemaAddDto.builder()
                .name(cinema.getName())
                .city(cinema.getCity())
                .build();
    }

    static FilmShowAddDto fromFilmShowToFilmShowAddDto(FilmShow filmShow){
        return filmShow == null ? null : FilmShowAddDto.builder()
                .cinemaHall(filmShow.getCinemaHall())
                .duration(filmShow.getDuration())
                .ticketsAvailable(filmShow.getTicketsAvailable())
                .startTime(filmShow.getStartTime())
                .build();
    }

    static FilmShow fromFilmShowAddDtoToFilmShow(FilmShowAddDto filmShowAddDto){
        return filmShowAddDto == null ? null : FilmShow.builder()
                .cinemaHall(filmShowAddDto.getCinemaHall())
                .duration(filmShowAddDto.getDuration())
                .ticketsAvailable(filmShowAddDto.getTicketsAvailable())
                .startTime(filmShowAddDto.getStartTime())
                .build();
    }

    static RepertoireAddDto fromRepertoireToRepertoireAddDto(Repertoire repertoire){
        return repertoire == null ? null : RepertoireAddDto.builder()
                .date(repertoire.getDate())
                .filmShows(new HashSet<>())
                .build();
    }

    static Repertoire fromRepertoireAddDtoToRepertoire(RepertoireAddDto repertoireAddDto){
        return repertoireAddDto == null ? null : Repertoire.builder()
                .cinema(repertoireAddDto.getCinemaAddDto() == null ? null : fromCinemaAddDtoToCinema(repertoireAddDto.getCinemaAddDto()))
                .date(repertoireAddDto.getDate())
                .filmShows(new HashSet<>())
                .build();
    }

    //dokonczyc








}
