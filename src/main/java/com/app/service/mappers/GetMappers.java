package com.app.service.mappers;

import com.app.dto.getDto.*;
import com.app.model.*;

import java.util.HashSet;

public interface GetMappers {

    static GetMovieDto fromMovieToGetMovieDto(Movie movie) {
        return movie == null ? null : GetMovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .usersAddedThisMovieToFavourites(new HashSet<>())
                .filmShows(new HashSet<>())
                .build();
    }

    static GetCinemaDto fromCinemaToGetCinemaDto(Cinema cinema) {
        return cinema == null ? null : GetCinemaDto.builder()
                .id(cinema.getId())
                .city(cinema.getCity())
                .name(cinema.getName())
                .repertoires(new HashSet<>())
                .build();
    }

    static GetCinemaHallDto fromCinemaHallToGetCinemaHallDto(CinemaHall cinemaHall) {
        return cinemaHall == null ? null : GetCinemaHallDto.builder()
                .id(cinemaHall.getId())
                .name(cinemaHall.getName())
                .cinemaHallType(cinemaHall.getType())
                .places(new HashSet<>())
                .getFilmShowDto(cinemaHall.getFilmShow() == null ? null : fromFilmShowToGetFilmShowDto(cinemaHall.getFilmShow()))
                .build();
    }

    static GetFilmShowDto fromFilmShowToGetFilmShowDto(FilmShow filmShow) {
        return filmShow == null ? null : GetFilmShowDto.builder()
                .id(filmShow.getId())
                .cinemaHall(filmShow.getCinemaHall() == null ? null : fromCinemaHallToGetCinemaHallDto(filmShow.getCinemaHall()))
                .ticketsAvailable(filmShow.getTicketsAvailable())
                .startTime(filmShow.getStartTime())
                .movie(filmShow.getMovie() == null ? null : fromMovieToGetMovieDto(filmShow.getMovie()))
                .tickets(new HashSet<>())
                .build();
    }

    static GetRepertoireDto fromRepertoireToGetRepertoireDto(Repertoire repertoire) {
        return repertoire == null ? null : GetRepertoireDto.builder()
                .id(repertoire.getId())
                .date(repertoire.getDate())
                .cinema(repertoire.getCinema() == null ? null : fromCinemaToGetCinemaDto(repertoire.getCinema()))
                .filmShows(new HashSet<>())
                .build();
    }

    static GetTicketDto fromTicketToGetTicketDto(Ticket ticket) {
        return ticket == null ? null : GetTicketDto.builder()
                .id(ticket.getId())
                .user(ticket.getUser() == null ? null : fromUserToGetUserDto(ticket.getUser()))
                .filmShow(ticket.getFilmShow() == null ? null : fromFilmShowToGetFilmShowDto(ticket.getFilmShow()))
                .build();
    }

    static GetUserDto fromUserToGetUserDto(User user) {
        return user == null ? null : GetUserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .build();
    }
}
