package com.app.service;

import com.app.dto.CinemaDto;
import com.app.dto.FilmShowDto;
import com.app.dto.MovieDto;
import com.app.dto.RepertoireDto;
import com.app.model.Cinema;
import com.app.model.FilmShow;
import com.app.model.Movie;
import com.app.model.Repertoire;

import java.util.HashSet;

public interface Mappers {

    static MovieDto fromMovieToMovieDto(Movie movie){
        return movie == null ? null : MovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .build();
    }

    static Movie fromMovieDtoToMovie(MovieDto movieDto){
        return movieDto == null ? null : Movie.builder()
                .id(movieDto.getId())
                .title(movieDto.getTitle())
                .description(movieDto.getDescription())
                .director(movieDto.getDirector())
                .duration(movieDto.getDuration())
                .genre(movieDto.getGenre())
                .build();
    }

    static Cinema fromCinemaDtoToCinema(CinemaDto cinemaDto){
        return cinemaDto == null ? null : Cinema.builder()
                .id(cinemaDto.getId())
                .name(cinemaDto.getName())
                .city(cinemaDto.getCity())
                .repertoires(new HashSet<>())
                .build();
    }

    static CinemaDto fromCinemaToCinemaDto(Cinema cinema){
        return cinema == null ? null : CinemaDto.builder()
                .id(cinema.getId())
                .name(cinema.getName())
                .city(cinema.getCity())
                .repertoiresDto(new HashSet<>())
                .build();
    }

    static FilmShowDto fromFilmShowToFilmShowDto(FilmShow filmShow){
        return filmShow == null ? null : FilmShowDto.builder()
                .id(filmShow.getId())
                .cinemaHall(filmShow.getCinemaHall())
                .duration(filmShow.getDuration())
                .movieDto(filmShow.getMovie() == null ? null : fromMovieToMovieDto(filmShow.getMovie()))
                .ticketsAvailable(filmShow.getTicketsAvailable())
                .startTime(filmShow.getStartTime())
                .repertoireDto(filmShow.getRepertoire() == null ? null : fromRepertoireToRepertoireDto(filmShow.getRepertoire()))
                .build();
    }

    static FilmShow fromFilmShowDtoToFilmShow(FilmShowDto filmShowDto){
        return filmShowDto == null ? null : FilmShow.builder()
                .id(filmShowDto.getId())
                .cinemaHall(filmShowDto.getCinemaHall())
                .duration(filmShowDto.getDuration())
                .movie(filmShowDto.getMovieDto() == null ? null : fromMovieDtoToMovie(filmShowDto.getMovieDto()))
                .ticketsAvailable(filmShowDto.getTicketsAvailable())
                .startTime(filmShowDto.getStartTime())
                .repertoire(filmShowDto.getRepertoireDto() == null ? null : fromRepertoireDtoToRepertoire(filmShowDto.getRepertoireDto()))
                .build();
    }

    static RepertoireDto fromRepertoireToRepertoireDto(Repertoire repertoire){
        return repertoire == null ? null : RepertoireDto.builder()
                .id(repertoire.getId())
                .cinemaDto(repertoire.getCinema() == null ? null : fromCinemaToCinemaDto(repertoire.getCinema()))
                .date(repertoire.getDate())
                .filmShows(new HashSet<>())
                .build();
    }

    static Repertoire fromRepertoireDtoToRepertoire(RepertoireDto repertoireDto){
        return repertoireDto == null ? null : Repertoire.builder()
                .id(repertoireDto.getId())
                .cinema(repertoireDto.getCinemaDto() == null ? null : fromCinemaDtoToCinema(repertoireDto.getCinemaDto()))
                .date(repertoireDto.getDate())
                .filmShows(new HashSet<>())
                .build();
    }







}
