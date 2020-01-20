package com.app.service.mappers;

import com.app.dto.downloadingDto.CinemaDownloadDto;
import com.app.dto.downloadingDto.FilmShowDownloadDto;
import com.app.dto.downloadingDto.MovieDownloadDto;
import com.app.dto.downloadingDto.RepertoireDownloadDto;
import com.app.model.Cinema;
import com.app.model.FilmShow;
import com.app.model.Movie;
import com.app.model.Repertoire;

import java.util.HashSet;

public interface DownloadMappers {

    static MovieDownloadDto fromMovieToMovieDownloadDto(Movie movie){
        return movie == null ? null : MovieDownloadDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .build();
    }

    static Movie fromMovieDownloadDtoToMovie(MovieDownloadDto movieDownloadDto){
        return movieDownloadDto == null ? null : Movie.builder()
                .id(movieDownloadDto.getId())
                .title(movieDownloadDto.getTitle())
                .description(movieDownloadDto.getDescription())
                .director(movieDownloadDto.getDirector())
                .duration(movieDownloadDto.getDuration())
                .genre(movieDownloadDto.getGenre())
                .build();
    }

    static Cinema fromCinemaDownloadDtoToCinema(CinemaDownloadDto cinemaDownloadDto){
        return cinemaDownloadDto == null ? null : Cinema.builder()
                .name(cinemaDownloadDto.getName())
                .city(cinemaDownloadDto.getCity())
                .repertoires(new HashSet<>())
                .build();
    }

    static CinemaDownloadDto fromCinemaToCinemaDownloadDto(Cinema cinema){
        return cinema == null ? null : CinemaDownloadDto.builder()
                .name(cinema.getName())
                .city(cinema.getCity())
                .build();
    }

    static FilmShowDownloadDto fromFilmShowToFilmShowDownloadDto(FilmShow filmShow){
        return filmShow == null ? null : FilmShowDownloadDto.builder()
                .cinemaHall(filmShow.getCinemaHall())
                .duration(filmShow.getDuration())
                .ticketsAvailable(filmShow.getTicketsAvailable())
                .startTime(filmShow.getStartTime())
                .build();
    }

    static FilmShow fromFilmShowDownloadDtoToFilmShow(FilmShowDownloadDto filmShowDownloadDto){
        return filmShowDownloadDto == null ? null : FilmShow.builder()
                .cinemaHall(filmShowDownloadDto.getCinemaHall())
                .duration(filmShowDownloadDto.getDuration())
                .ticketsAvailable(filmShowDownloadDto.getTicketsAvailable())
                .startTime(filmShowDownloadDto.getStartTime())
                .build();
    }

    static RepertoireDownloadDto fromRepertoireToRepertoireDownloadDto(Repertoire repertoire){
        return repertoire == null ? null : RepertoireDownloadDto.builder()
                .date(repertoire.getDate())
                .filmShows(new HashSet<>())
                .build();
    }

    static Repertoire fromRepertoireDownloadDtoToRepertoire(RepertoireDownloadDto repertoireDownloadDto){
        return repertoireDownloadDto == null ? null : Repertoire.builder()
                .date(repertoireDownloadDto.getDate())
                .filmShows(new HashSet<>())
                .build();
    }

}
