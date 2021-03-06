package com.app.service.mappers;

import com.app.dto.createDto.*;
import com.app.model.*;

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
                .name(createCinemaDto.getName())
                .build();
    }

    static CinemaHall fromCreateCinemaHallDtoToCinemaHall(CreateCinemaHallDto cinemaHallDto) {
        return cinemaHallDto == null ? null : CinemaHall.builder()
                .name(cinemaHallDto.getName())
                .type(cinemaHallDto.getCinemaHallType())
                .filmShow(new HashSet<>())
                .places(new HashSet<>())
                .build();
    }

    static FilmShow fromCreateFilmShowDtoToFilmShow(CreateFilmShowDto createFilmShowDto) {
        return createFilmShowDto == null ? null : FilmShow.builder()
                .startTime(createFilmShowDto.getStartTime())
                .build();
    }

    static Repertoire fromCreateRepertoireDtoToRepertoire(CreateRepertoireDto createRepertoireDto) {
        return createRepertoireDto == null ? null : Repertoire.builder()
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

    static Place fromCreatePlaceDtoToPlace(CreatePlaceDto createPlaceDto) {
        return createPlaceDto == null ? null : Place.builder()
                .rowNum(createPlaceDto.getRow())
                .number(createPlaceDto.getNumber())
                .available(createPlaceDto.getAvailable())
                .build();
    }

    static Role fromCreateRoleDtoToRole(CreateRoleDto roleDto) {
        return roleDto == null ? null : Role.builder()
                .name(roleDto.getName().toUpperCase())
                .build();
    }

    static TicketType fromCreateTicketTypeDtoToTicketType(CreateTicketTypeDto createTicketTypeDto) {
        return createTicketTypeDto == null ? null : TicketType.builder()
                .name(createTicketTypeDto.getName())
                .price(createTicketTypeDto.getPrice())
                .build();
    }

}
