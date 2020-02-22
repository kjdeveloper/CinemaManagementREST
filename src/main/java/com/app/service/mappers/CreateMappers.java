package com.app.service.mappers;

import com.app.dto.createDto.*;
import com.app.model.*;

import java.util.ArrayList;
import java.util.HashSet;

public interface CreateMappers {

    static Movie fromCreateMovieDtoToMovie(CreateMovieDto createMovieDto) {
        return createMovieDto == null ? null : Movie.builder()
                .title(createMovieDto.getTitle())
                .description(createMovieDto.getDescription())
                .director(createMovieDto.getDirector())
                .duration(createMovieDto.getDuration())
                .genre(createMovieDto.getGenre())
                .build();
    }

    static Cinema fromCreateCinemaDtoToCinema(CreateCinemaDto createCinemaDto) {
        return createCinemaDto == null ? null : Cinema.builder()
                .city(createCinemaDto.getCity())
                .build();
    }

    static CinemaHall fromCreateCinemaHallDtoToCinemaHall(CreateCinemaHallDto cinemaHallDto) {
        return cinemaHallDto == null ? null : CinemaHall.builder()
                .places(new HashSet<>())
                .filmShow(cinemaHallDto.getCreateFilmShowDto() == null ? null : fromCreateFilmShowDtoToFilmShow(cinemaHallDto.getCreateFilmShowDto()))
                .build();
    }

    static FilmShow fromCreateFilmShowDtoToFilmShow(CreateFilmShowDto createFilmShowDto) {
        return createFilmShowDto == null ? null : FilmShow.builder()
                .cinemaHall(createFilmShowDto.getCinemaHall() == null ? null : fromCreateCinemaHallDtoToCinemaHall(createFilmShowDto.getCinemaHall()))
                .startTime(createFilmShowDto.getStartTime())
                .movie(createFilmShowDto.getMovie() == null ? null : fromCreateMovieDtoToMovie(createFilmShowDto.getMovie()))
                .build();
    }

    static Repertoire fromCreateRepertoireDtoToRepertoire(CreateRepertoireDto createRepertoireDto) {
        return createRepertoireDto == null ? null : Repertoire.builder()
                .cinema(createRepertoireDto.getCinema() == null ? null : fromCreateCinemaDtoToCinema(createRepertoireDto.getCinema()))
                .date(createRepertoireDto.getDate())
                .filmShows(new HashSet<>())
                .build();
    }

    static User fromCreateUserDtoToUser(CreateUserDto userDto) {
        return userDto == null ? null : User.builder()
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .age(userDto.getAge())
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .roles(new HashSet<>())
                .build();
    }

    static Ticket fromCreateTicketDtoToTicket(CreateTicketDto ticketDto) {
        return ticketDto == null ? null : Ticket.builder()
                .ticketType(ticketDto.getTicketType())
                .build();
    }


}
