package com.app.service.mappers;

import com.app.dto.getDto.*;
import com.app.model.*;

import java.util.HashSet;
import java.util.stream.Collectors;

public interface GetMappers {

    static GetMovieDto fromMovieToGetMovieDto(Movie movie) {
        return movie == null ? null : GetMovieDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .duration(movie.getDuration())
                .genre(movie.getGenre())
                .version(movie.getVersion())
                .build();
    }

    static GetCinemaDto fromCinemaToGetCinemaDto(Cinema cinema) {
        return cinema == null ? null : GetCinemaDto.builder()
                .id(cinema.getId())
                .city(cinema.getCity())
                .name(cinema.getName())
                .version(cinema.getVersion())
                .build();
    }

    static GetCinemaHallDto fromCinemaHallToGetCinemaHallDto(CinemaHall cinemaHall) {
        return cinemaHall == null ? null : GetCinemaHallDto.builder()
                .id(cinemaHall.getId())
                .name(cinemaHall.getName())
                .version(cinemaHall.getVersion())
                .cinemaHallType(cinemaHall.getType())
                .cinemaDto(cinemaHall.getCinema() == null ? null : fromCinemaToGetCinemaDto(cinemaHall.getCinema()))
                .places(cinemaHall
                        .getPlaces()
                        .stream()
                        .map(GetMappers::fromPlaceToGetPlaceDto)
                        .collect(Collectors.toSet()).size())
                .build();
    }

    static GetFilmShowDto fromFilmShowToGetFilmShowDto(FilmShow filmShow) {
        return filmShow == null ? null : GetFilmShowDto.builder()
                .id(filmShow.getId())
                .cinemaHall(filmShow.getCinemaHall() == null ? null : fromCinemaHallToGetCinemaHallDto(filmShow.getCinemaHall()))
                .ticketsAvailable(filmShow.getTicketsAvailable())
                .startTime(filmShow.getStartTime())
                .movie(filmShow.getMovie() == null ? null : fromMovieToGetMovieDto(filmShow.getMovie()))
                .version(filmShow.getVersion())
                .build();
    }

    static GetRepertoireDto fromRepertoireToGetRepertoireDto(Repertoire repertoire) {
        return repertoire == null ? null : GetRepertoireDto.builder()
                .id(repertoire.getId())
                .date(repertoire.getDate())
                .cinemaDto(repertoire.getCinema() == null ? null : fromCinemaToGetCinemaDto(repertoire.getCinema()))
                .filmShowDto(new HashSet<>())
                .version(repertoire.getVersion())
                .build();
    }

    static GetTicketDto fromTicketToGetTicketDto(Ticket ticket) {
        return ticket == null ? null : GetTicketDto.builder()
                .id(ticket.getId())
                .version(ticket.getVersion())
                .price(ticket.getPrice())
                .dateOfPurchase(ticket.getDateOfPurchase())
                .ticketTypeDto(ticket.getTicketType() == null ? null : fromTicketTypeToGetTicketTypeDto(ticket.getTicketType()))
                .places(new HashSet<>())
                .user(ticket.getUser() == null ? null : fromUserToGetUserDto(ticket.getUser()))
                .filmShow(ticket.getFilmShow() == null ? null : fromFilmShowToGetFilmShowDto(ticket.getFilmShow()))
                .cinema(ticket.getCinema() == null ? null : fromCinemaToGetCinemaDto(ticket.getCinema()))
                .build();
    }

    static GetUserDto fromUserToGetUserDto(User user) {
        return user == null ? null : GetUserDto.builder()
                .id(user.getId())
                .role(new HashSet<>())
                .version(user.getVersion())
                .username(user.getUsername())
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .version(user.getVersion())
                .build();
    }

    static GetRoleDto fromRoleToGetRoleDto(Role role) {
        return role == null ? null : GetRoleDto.builder()
                .id(role.getId())
                .version(role.getVersion())
                .name(role.getName())
                .build();
    }

    static GetPlaceDto fromPlaceToGetPlaceDto(Place place) {
        return place == null ? null : GetPlaceDto
                .builder()
                .id(place.getId())
                .version(place.getVersion())
                .row_num(place.getRowNum())
                .number(place.getNumber())
                .available(place.getAvailable())
                .build();
    }

    static GetHistoryTicketDto fromTicketToGetHistoryTicket(Ticket ticket) {
        return ticket == null ? null : GetHistoryTicketDto
                .builder()
                .ticketTypeDto(ticket.getTicketType() == null ? null : fromTicketTypeToGetTicketTypeDto(ticket.getTicketType()))
                .price(ticket.getTicketType().getPrice())
                .filmShow(ticket.getFilmShow().getMovie().toString())
                .build();
    }

    static GetTicketTypeDto fromTicketTypeToGetTicketTypeDto(TicketType ticketType) {
        return ticketType == null ? null : GetTicketTypeDto.builder()
                .id(ticketType.getId())
                .version(ticketType.getVersion())
                .name(ticketType.getName())
                .build();
    }
}
