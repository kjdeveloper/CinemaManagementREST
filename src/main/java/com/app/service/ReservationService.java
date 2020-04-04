package com.app.service;

import com.app.dto.createDto.CreateReservationDto;
import com.app.dto.getDto.GetReservationDto;
import com.app.dto.getDto.GetTicketDto;
import com.app.exception.AppException;
import com.app.model.*;
import com.app.repository.*;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final FilmShowRepository filmShowRepository;
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final TicketTypeRepository ticketTypeRepository;
    private final TicketRepository ticketRepository;

    private final TicketService ticketService;

    public String reserveTicket(Long userId, Set<CreateReservationDto> reservationsDto) {
        if (Objects.isNull(reservationsDto)) {
            throw new AppException("List of reservations is null");
        }
        Set<Ticket> tickets = new HashSet<>();
        Integer reservedPlaces = reservationsDto.size();

        Long filmShowId = reservationsDto
                .stream()
                .map(CreateReservationDto::getFilmShowId)
                .findFirst()
                .orElse(null);

        FilmShow filmShow = filmShowRepository.findById(filmShowId)
                .orElseThrow(() -> new AppException("Film show doesn't exist"));

        Set<Place> availablePlaces = ticketService.getJustAvailablePlacesOnFilmShow(filmShow);

        if (availablePlaces.size() < reservedPlaces) {
            throw new AppException("There is not enough available seats");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        for (CreateReservationDto reservation : reservationsDto) {
            TicketType ticketType = ticketTypeRepository.findById(reservation.getTicketTypeId())
                    .orElseThrow(() -> new AppException("Ticket type doesn't exist"));

            Place place = ticketService.getPlaceFromAvailablePlaces(filmShow, reservation.getPlaceId());

            Ticket ticket = Ticket.builder()
                    .ticketType(ticketType)
                    .filmShow(filmShow)
                    .cinema(filmShow.getCinemaHall().getCinema())
                    .user(user)
                    .place(place)
                    .reservation(true)
                    .build();

            place.setAvailable(false);
            placeRepository.save(place);
            ticketRepository.save(ticket);
            tickets.add(ticket);
        }

        filmShow.setTicketsAvailable(filmShow.getTicketsAvailable() - reservedPlaces);

        List<GetTicketDto> reservedTickets = tickets
                .stream()
                .map(GetMappers::fromTicketToGetTicketDto)
                .collect(Collectors.toList());

        return "Dear user " + user.getUsername() +
                ". You just reserved: " + reservedTickets.size() + " place" +
                " your reservation: " + reservedTickets +
                "'. Remember that you must come 30 minutes before film show to pick up these tickets. Otherwise they will be lost.";
    }

   /* public String getReservation(Long reservationId) {

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

        List<CreateTicketDto> createTicketDtoList = new ArrayList<>();

        for (GetTicketDto ticketDto : getReservationDto.getTickets()) {
            CreateTicketDto createTicketDto = CreateTicketDto.builder()
                    .cinemaId(ticketDto.getCinema().getId())
                    .filmShowId(ticketDto.getFilmShow().getId())
                    .placeId(ticketDto.getPlace().getId())
                    .userId(ticketDto.getUser().getId())
                    .ticketTypeId(ticketDto.getId())
                    .build();


            createTicketDtoList.add(createTicketDto);
        }
        return ticketService.buyTickets(createTicketDtoList);
    }*/
}
