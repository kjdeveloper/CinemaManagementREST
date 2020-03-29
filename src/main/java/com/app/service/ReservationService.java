package com.app.service;

import com.app.dto.createDto.CreateReservationDto;
import com.app.dto.createDto.CreateTicketDto;
import com.app.dto.getDto.GetReservationDto;
import com.app.dto.getDto.GetTicketDto;
import com.app.exception.AppException;
import com.app.model.*;
import com.app.model.enums.TicketType;
import com.app.repository.FilmShowRepository;
import com.app.repository.PlaceRepository;
import com.app.repository.ReservationRepository;
import com.app.repository.UserRepository;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final FilmShowRepository filmShowRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final ReservationRepository reservationRepository;

    private final TicketService ticketService;

    public String reserveTicket(CreateReservationDto reservationDto) {

        Long filmShowId = reservationDto.getFilmShowId();
        Long userId = reservationDto.getUserId();
        Set<Long> placesId = reservationDto.getPlacesId();

        if (Objects.isNull(userId)) {
            throw new AppException("User id is null");
        }
        if (Objects.isNull(filmShowId)) {
            throw new AppException("Film Show id is null");
        }
        if (Objects.isNull(placesId)) {
            throw new AppException("Places id is null");
        }

        FilmShow filmShow = filmShowRepository.findById(filmShowId)
                .orElseThrow(() -> new AppException("Film Show with given id doesn't exist"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        Set<Place> availablePlaces = ticketService.getJustAvailablePlacesOnFilmShow(filmShow);

        Integer reservedPlaces = placesId.size();
        if (availablePlaces.size() < reservedPlaces) {
            throw new AppException("There is not enough available seats");
        }
        filmShow.setTicketsAvailable(filmShow.getTicketsAvailable() - reservedPlaces);

        Set<Ticket> tickets = new HashSet<>();

        for (Long id : placesId) {
            Place place = ticketService.getPlaceFromAvailablePlaces(filmShow, id);

            TicketType ticketType = ticketService.getTicketTypeFromCustomer();

            Ticket ticket = Ticket.builder()
                    .ticketType(ticketType)
                    .filmShow(filmShow)
                    .cinema(filmShow.getCinemaHall().getCinema())
                    .user(user)
                    .place(place)
                    .price(ticketType.getPrice())
                    .build();

            place.setAvailable(false);
            placeRepository.save(place);
            tickets.add(ticket);
        }
        Reservation reservation = Reservation
                .builder()
                .tickets(tickets)
                .build();

        reservationRepository.save(reservation);

        GetReservationDto getReservationDto = GetReservationDto.builder()
                .id(reservation.getId())
                .tickets(reservation.getTickets()
                        .stream()
                        .map(GetMappers::fromTicketToGetTicketDto)
                        .collect(Collectors.toSet()))
                .build();

        return "Dear user " + user.getUsername() +
                ". You just reserved: " + reservedPlaces +
                " your reservation: " + getReservationDto +
                " for '" + filmShow.getMovie() +
                "'. Remember that you must come 30 minutes before film show to pick up these tickets. Otherwise they will be lost.";
    }

    public String getReservation(Long reservationId) {

        if (Objects.isNull(reservationId)) {
            throw new AppException("Id is null");
        }

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new AppException("Reservation doesn't exist"));

        Set<Ticket> tickets = reservation.getTickets()
                .stream()
                .filter(t -> t.getFilmShow()
                        .getStartTime()
                        .isAfter(LocalDateTime.now()
                                .minusMinutes(25)))
                .collect(Collectors.toSet());
        
        if (!tickets.isEmpty()) {
            reservationRepository.delete(reservation);
            throw new AppException("The reservation has been canceled due to late pickup");
        }

        GetReservationDto getReservationDto = GetReservationDto.builder()
                .id(reservation.getId())
                .tickets(reservation.getTickets()
                        .stream()
                        .map(GetMappers::fromTicketToGetTicketDto)
                        .collect(Collectors.toSet()))
                .build();

        String result = null;

        for (GetTicketDto ticketDto : getReservationDto.getTickets()) {
            CreateTicketDto createTicketDto = CreateTicketDto.builder()
                    .cinemaId(ticketDto.getCinema().getId())
                    .filmShowId(ticketDto.getFilmShow().getId())
                    .placeId(ticketDto.getPlace().getId())
                    .price(ticketDto.getPrice())
                    .userId(ticketDto.getUser().getId())
                    .ticketType(ticketDto.getTicketType())
                    .build();

            result = ticketService.buyTickets(getReservationDto.getTickets().size(), createTicketDto);
        }
        return result;
    }

}
