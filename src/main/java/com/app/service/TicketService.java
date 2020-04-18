package com.app.service;

import com.app.dto.createDto.*;
import com.app.dto.getDto.GetHistoryTicketDto;
import com.app.dto.getDto.GetTicketDto;
import com.app.exception.AppException;
import com.app.model.*;
import com.app.repository.*;
import com.app.service.file.FileService;
import com.app.service.mail.MailService;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FilmShowRepository filmShowRepository;
    private final CinemaRepository cinemaRepository;
    private final PlaceRepository placeRepository;
    private final TicketTypeRepository ticketTypeRepository;

    private final MailService mailService;

    public List<GetTicketDto> getAll() {
        return ticketRepository.findAll()
                .stream()
                .map(GetMappers::fromTicketToGetTicketDto)
                .collect(Collectors.toList());
    }


    Set<Place> getJustAvailablePlacesOnFilmShow(FilmShow filmShow) {
        if (Objects.isNull(filmShow)) {
            throw new AppException("Film show is null");
        }

        return placeRepository
                .findByCinemaHall_Id(filmShow.getCinemaHall().getId())
                .stream()
                .filter(Place::getAvailable)
                .collect(Collectors.toSet());
    }

    Place getPlaceFromAvailablePlaces(FilmShow filmShow, Long placeId) {
        if (Objects.isNull(filmShow)) {
            throw new AppException("Film show is null");
        }

        if (Objects.isNull(placeId)) {
            throw new AppException("Place is null");
        }

        return getJustAvailablePlacesOnFilmShow(filmShow)
                .stream()
                .filter(pla -> pla.getId().equals(placeId))
                .findFirst()
                .orElseThrow(() -> new AppException("Place is not available. Please choose a different place."));
    }

    public String buySingleTicket(CreateTicketDto ticketDto) {
        if (Objects.isNull(ticketDto)) {
            throw new AppException("ticket is null");
        }

        FilmShow filmShow = filmShowRepository.findById(ticketDto.getFilmShowId())
                .orElseThrow(() -> new AppException("Film Show doesn't exist"));

        Set<Place> availablePlaces = getJustAvailablePlacesOnFilmShow(filmShow);

        if (availablePlaces.size() < 1) {
            throw new AppException("There is not enough available seats");
        }

        User user = userRepository.findById(ticketDto.getUserId())
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        Place place = getPlaceFromAvailablePlaces(filmShow, ticketDto.getPlaceId());

        TicketType ticketType = ticketTypeRepository.findById(ticketDto.getTicketTypeId())
                .orElseThrow(() -> new AppException("Ticket type doesn't exist"));

        Ticket ticket = Ticket.builder()
                .dateOfPurchase(LocalDate.now())
                .ticketType(ticketType)
                .price(ticketType.getPrice())
                .places(Set.of(place))
                .placeId(place.getId())
                .user(user)
                .cinema(filmShow.getCinemaHall().getCinema())
                .filmShow(filmShow)
                .reservation(false)
                .build();

        filmShow.setTicketsAvailable(filmShow.getTicketsAvailable() - 1);
        place.setAvailable(false);
        place.setTicket(ticket);
        placeRepository.save(place);
        ticketRepository.save(ticket);

        return "Dear user " + user.getUsername() +
                ". You just bought a ticket worth: " + ticketType.getPrice() +
                " for '" + filmShow.getMovie() + "'." + " on place number: " + place.getId();
    }

    private BigDecimal countPrice(Set<GetTicketDto> tickets) {
        return tickets
                .stream()
                .map(GetTicketDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String buyTickets(List<CreateTicketDto> ticketsDto) {

        CreateTicketDto ticketDto = ticketsDto.get(0);
        Integer quantity = ticketsDto.size();

        FilmShow filmShow = filmShowRepository.findById(ticketDto.getFilmShowId())
                .orElseThrow(() -> new AppException("Film Show doesn't exist"));

        Set<Place> availablePlaces = getJustAvailablePlacesOnFilmShow(filmShow);

        if (availablePlaces.size() < quantity) {
            throw new AppException("There is not enough available seats");
        }

        User user = userRepository.findById(ticketDto.getUserId())
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        Set<Ticket> tickets = new HashSet<>();
        for (CreateTicketDto ticketDto1 : ticketsDto) {
            TicketType ticketType = ticketTypeRepository.findById(ticketDto1.getTicketTypeId())
                    .orElseThrow(() -> new AppException("Ticket type doesn't exist"));

            Place place = getPlaceFromAvailablePlaces(filmShow, ticketDto1.getPlaceId());

            Ticket ticket = Ticket.builder()
                    .dateOfPurchase(LocalDate.now())
                    .ticketType(ticketType)
                    .price(ticketType.getPrice())
                    .filmShow(filmShow)
                    .placeId(place.getId())
                    .cinema(filmShow.getCinemaHall().getCinema())
                    .user(user)
                    .places(Set.of(place))
                    .reservation(false)
                    .build();

            place.setTicket(ticket);
            place.setAvailable(false);
            placeRepository.save(place);
            ticketRepository.save(ticket);
            tickets.add(ticket);
        }

        filmShow.setTicketsAvailable(filmShow.getTicketsAvailable() - quantity);

        Set<GetTicketDto> boughtTickets = tickets
                .stream()
                .map(GetMappers::fromTicketToGetTicketDto)
                .collect(Collectors.toSet());

        Set<Long> placesId = tickets
                .stream()
                .flatMap(ticketPl -> ticketPl.getPlaces().stream())
                .map(Place::getId)
                .collect(Collectors.toSet());

        BigDecimal ticketsPrice = countPrice(boughtTickets);

        return "Dear user " + user.getUsername() +
                ". You just bought a tickets worth: " + ticketsPrice +
                " for '" + filmShow.getMovie() + "'."  + " on place numbers: " + placesId;
    }

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
                .orElseThrow(() -> new AppException("Film show id is null"));

        FilmShow filmShow = filmShowRepository.findById(filmShowId)
                .orElseThrow(() -> new AppException("Film show doesn't exist"));

        Set<Place> availablePlaces = getJustAvailablePlacesOnFilmShow(filmShow);

        if (availablePlaces.size() < reservedPlaces) {
            throw new AppException("There is not enough available seats");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        for (CreateReservationDto reservation : reservationsDto) {
            TicketType ticketType = ticketTypeRepository.findById(reservation.getTicketTypeId())
                    .orElseThrow(() -> new AppException("Ticket type doesn't exist"));

            Place place = getPlaceFromAvailablePlaces(filmShow, reservation.getPlaceId());

            Ticket ticket = Ticket.builder()
                    .ticketType(ticketType)
                    .price(ticketType.getPrice())
                    .dateOfPurchase(LocalDate.now())
                    .filmShow(filmShow)
                    .cinema(filmShow.getCinemaHall().getCinema())
                    .user(user)
                    .placeId(place.getId())
                    .places(Set.of(place))
                    .reservation(true)
                    .build();

            place.setTicket(ticket);
            place.setAvailable(false);
            placeRepository.save(place);
            ticketRepository.save(ticket);
            tickets.add(ticket);
        }

        filmShow.setTicketsAvailable(filmShow.getTicketsAvailable() - reservedPlaces);

        Set<GetTicketDto> reservedTickets = tickets
                .stream()
                .map(GetMappers::fromTicketToGetTicketDto)
                .collect(Collectors.toSet());

        BigDecimal price = countPrice(reservedTickets);

        return "Dear user " + user.getUsername() +
                ". You just reserved: " + reservedTickets.size() + " place(s)" +
                " your reservation will be cost: " + price + ". Details: " + reservedTickets +
                ". Remember that you must come 30 minutes before film show to pick up these tickets. Otherwise they will be lost.";
    }

    public void deleteCollection(List<Ticket> tickets) {
        if (Objects.isNull(tickets)) {
            throw new AppException("Tickets is null");
        }
        ticketRepository.deleteAll(tickets);
    }

    public Long deleteById(Long id) {
        if (Objects.isNull(id)) {
            throw new AppException("Id is null");
        }

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new AppException("Ticket doesn't exist"));

        ticketRepository.delete(ticket);
        return ticket.getId();
    }

    public Long deleteAll() {
        long rows = ticketRepository.count();

        ticketRepository.deleteAll();
        return rows;
    }

    //=================================================================================================

    //===================================History statistics============================================

    //=================================================================================================
    public List<GetHistoryTicketDto> getHistoryOfTickets(CreateHistoryTicketDto createHistoryTicketDto) {
        Long userId = createHistoryTicketDto.getUserId();
        Boolean sendMail = createHistoryTicketDto.getSendMail();
        Boolean saveToFile = createHistoryTicketDto.getSaveToFile();

        if (Objects.isNull(userId)) {
            throw new AppException("id is null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        final String fileName = "Tickets history" + LocalDate.now().toString() + "t";

        List<GetHistoryTicketDto> tickets = user.getTickets().
                stream()
                .map(GetMappers::fromTicketToGetHistoryTicket)
                .collect(Collectors.toList());

        if (sendMail) {
            mailService.sendEmail(fileName, tickets);
        }

        if (saveToFile) {
            FileService.saveToFile(fileName, tickets);
        }
        return tickets;
    }

    public List<GetHistoryTicketDto> getHistoryOfTicketsByPrice(CreateHistoryByPriceDto historyByPriceDto) {

        Long userId = historyByPriceDto.getUserId();
        BigDecimal from = historyByPriceDto.getFrom();
        BigDecimal to = historyByPriceDto.getTo();
        Boolean sendMail = historyByPriceDto.getSendMail();
        Boolean saveToFile = historyByPriceDto.getGetFile();

        if (Objects.isNull(userId)) {
            throw new AppException("id is null");
        }
        if (Objects.isNull(from) || from.compareTo(BigDecimal.ZERO) < 0) {
            throw new AppException("Lower price is not correct");
        }
        if (Objects.isNull(to) || to.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppException("Higher price cannot be null or less 0");
        }
        if (to.compareTo(from) < 0) {
            throw new AppException("Lower price cannot be higher");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        String subject = "Tickets history between prices" + LocalDate.now().toString() + "t";

        List<GetHistoryTicketDto> tickets = user.getTickets()
                .stream()
                .filter(p -> p.getTicketType().getPrice().compareTo(from) >= 0 && p.getTicketType().getPrice().compareTo(to) <= 0)
                .map(GetMappers::fromTicketToGetHistoryTicket)
                .collect(Collectors.toList());

        if (sendMail) {
            mailService.sendEmail(subject, tickets);
        }

        if (saveToFile) {
            FileService.saveToFile(subject, tickets);
        }

        return tickets;
    }

    public List<GetHistoryTicketDto> getHistoryOfTicketsByDate(CreateHistoryByDateDto historyByDateDto) {

        Long userId = historyByDateDto.getUserId();
        LocalDate from = historyByDateDto.getFrom();
        LocalDate to = historyByDateDto.getTo();
        Boolean sendMail = historyByDateDto.getSendMail();
        Boolean saveToFile = historyByDateDto.getGetFile();

        if (Objects.isNull(userId)) {
            throw new AppException("id is null");
        }
        if (Objects.isNull(from)) {
            throw new AppException("'From' date is null");
        }
        if (Objects.isNull(to)) {
            throw new AppException("'To' date is null");
        }
        if (to.compareTo(from) < 0) {
            throw new AppException("'From' date cannot be after 'to' date");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        String subject = "Tickets history between dates" + LocalDate.now().toString() + "t";

        List<GetHistoryTicketDto> tickets = user.getTickets()
                .stream()
                .filter(p -> p.getFilmShow().getRepertoire().getDate().compareTo(from) >= 0 && p.getFilmShow().getRepertoire().getDate().compareTo(to) < 0)
                .map(GetMappers::fromTicketToGetHistoryTicket)
                .collect(Collectors.toList());

        if (sendMail) {
            mailService.sendEmail(subject, tickets);
        }

        if (saveToFile) {
            FileService.saveToFile(subject, tickets);
        }

        return tickets;
    }
}
