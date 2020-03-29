package com.app.service;

import com.app.dto.createDto.CreateHistoryTicketDto;
import com.app.dto.createDto.CreateTicketDto;
import com.app.dto.createDto.CreateHistoryByDateDto;
import com.app.dto.createDto.CreateHistoryByPriceDto;
import com.app.dto.getDto.GetHistoryTicketDto;
import com.app.dto.getDto.GetTicketDto;
import com.app.exception.AppException;
import com.app.model.FilmShow;
import com.app.model.Place;
import com.app.model.Ticket;
import com.app.model.User;
import com.app.model.enums.TicketType;
import com.app.repository.*;
import com.app.service.mappers.CreateMappers;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private final CinemaHallRepository cinemaHallRepository;
    private final PlaceRepository placeRepository;

    private final MailService mailService;

    Set<Place> getJustAvailablePlacesOnFilmShow(FilmShow filmShow) {
        return cinemaHallRepository
                .findCinemaHallByCinema_Id(filmShow.getRepertoire().getCinema().getId())
                .stream()
                .filter(f -> f.getFilmShow().getId().equals(filmShow.getId()))
                .flatMap(c -> c.getPlaces()
                        .stream())
                .filter(Place::getAvailable)
                .collect(Collectors.toSet());
    }

    Place getPlaceFromAvailablePlaces(FilmShow filmShow, Long placeId) {
        return getJustAvailablePlacesOnFilmShow(filmShow)
                .stream()
                .filter(pla -> pla.getId().equals(placeId))
                .findFirst()
                .orElseThrow(() -> new AppException("Place doesn't exist"));
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

        if (cinemaRepository.findById(ticketDto.getCinemaId()).isEmpty()) {
            throw new AppException("Cinema with given id doesn't exist");
        }

        User user = userRepository.findById(ticketDto.getUserId())
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        Place place = getPlaceFromAvailablePlaces(filmShow, ticketDto.getPlaceId());
        place.setAvailable(false);
        placeRepository.save(place);

        Ticket ticket = CreateMappers.fromCreateTicketDtoToTicket(ticketDto);
        ticketRepository.save(ticket);

        filmShow.setTicketsAvailable(filmShow.getTicketsAvailable() - 1);
        filmShowRepository.save(filmShow);

        return "Dear user " + user.getUsername() +
                ". You just bought a ticket worth: " + ticket.getPrice() +
                "for '" + filmShow.getMovie() + "'.";
    }

    private BigDecimal countPrice(Set<GetTicketDto> tickets) {
        return tickets
                .stream()
                .map(GetTicketDto::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    TicketType getTicketTypeFromCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What kind of ticket you want to buy? (NORMAL/REDUCE/FAMILY)");
        return TicketType.valueOf(scanner.nextLine());
    }

    public String buyTickets(int quantity, CreateTicketDto ticketDto) {
        FilmShow filmShow = filmShowRepository.findById(ticketDto.getFilmShowId())
                .orElseThrow(() -> new AppException("Film Show doesn't exist"));

        Set<Place> availablePlaces = getJustAvailablePlacesOnFilmShow(filmShow);

        if (availablePlaces.size() < quantity) {
            throw new AppException("There is not enough available seats");
        }

        User user = userRepository.findById(ticketDto.getUserId())
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        Set<Ticket> tickets = new HashSet<>();

        for (int i = 0; i < quantity; i++) {
            Place place = getPlaceFromAvailablePlaces(filmShow, ticketDto.getPlaceId());

            TicketType ticketType = getTicketTypeFromCustomer();
            Ticket ticket = Ticket.builder()
                    .ticketType(ticketType)
                    .filmShow(filmShow)
                    .cinema(filmShow.getCinemaHall().getCinema())
                    .user(user)
                    .place(place)
                    .price(ticketType.getPrice())
                    .ticketType(ticketDto.getTicketType())
                    .build();

            ticketRepository.save(ticket);

            place.setAvailable(false);
            placeRepository.save(place);

            tickets.add(ticket);
        }

        filmShow.setTicketsAvailable(filmShow.getTicketsAvailable() - quantity);

        Set<GetTicketDto> boughtTickets = tickets
                .stream()
                .map(GetMappers::fromTicketToGetTicketDto)
                .collect(Collectors.toSet());

        BigDecimal ticketsPrice = countPrice(boughtTickets);

        return "Dear user " + user.getUsername() +
                ". You just bought a tickets worth: " + ticketsPrice +
                " for '" + filmShow.getMovie() + "'.";
    }

    public List<GetHistoryTicketDto> getHistoryOfTickets(CreateHistoryTicketDto createHistoryTicketDto) {
        Long userId = createHistoryTicketDto.getUserId();
        Boolean sendMail = createHistoryTicketDto.getSendMail();
        Boolean saveToFile = createHistoryTicketDto.getSaveToFile();

        if (Objects.isNull(userId)) {
            throw new AppException("id is null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        final String subject = "Tickets history";

        List<GetHistoryTicketDto> tickets = user.getTickets().
                stream()
                .map(GetMappers::fromTicketToGetHistoryTicket)
                .collect(Collectors.toList());

        if (sendMail) {
            mailService.sendEmail(subject, tickets);
        }

        if (saveToFile) {
            FileService.saveToFile(subject + "/" + LocalDateTime.now(), tickets);
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
        if (from.compareTo(to) < 0) {
            throw new AppException("Lower price cannot be higher");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        String subject = "Tickets history from :" + from + " to :" + to + " zl";

        List<GetHistoryTicketDto> tickets = new ArrayList<>();

        user.getTickets()
                .stream()
                .filter(p -> p.getPrice().compareTo(from) > 0 && p.getPrice().compareTo(to) < 0)
                .forEach(t -> {
                    tickets.add(GetHistoryTicketDto.builder()
                            .price(t.getPrice())
                            .ticketType(t.getTicketType())
                            .filmShow(t.getFilmShow())
                            .build());
                });

        if (sendMail) {
            mailService.sendEmail(subject, tickets);
        }

        if (saveToFile) {
            FileService.saveToFile(subject + "/" + LocalDateTime.now(), tickets);
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

        if (Objects.isNull(from) || from.compareTo(LocalDate.now()) > 0) {
            throw new AppException("'From' date is not correct");
        }
        if (Objects.isNull(to) || to.compareTo(LocalDate.now()) > 0) {
            throw new AppException("'To' date is not correct");
        }
        if (from.compareTo(to) < 0) {
            throw new AppException("'From' date cannot be after 'to' date");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        String subject = "Tickets history from date " + from + " until " + to + "";

        List<GetHistoryTicketDto> tickets = new ArrayList<>();

        user.getTickets()
                .stream()
                .filter(p -> p.getFilmShow().getRepertoire().getDate().compareTo(from) > 0 && p.getFilmShow().getRepertoire().getDate().compareTo(to) < 0)
                .forEach(t -> {
                    tickets.add(GetHistoryTicketDto.builder()
                            .price(t.getPrice())
                            .ticketType(t.getTicketType())
                            .filmShow(t.getFilmShow())
                            .build());
                });

        if (sendMail) {
            mailService.sendEmail(subject, tickets);
        }

        if (saveToFile) {
            FileService.saveToFile(subject + "/" + LocalDateTime.now(), tickets);
        }

        return tickets;
    }


}
