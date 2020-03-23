package com.app.service;

import com.app.dto.createDto.CreateTicketDto;
import com.app.dto.getDto.GetHistoryTicket;
import com.app.dto.getDto.GetTicketDto;
import com.app.exception.AppException;
import com.app.model.FilmShow;
import com.app.model.Repertoire;
import com.app.model.Ticket;
import com.app.model.User;
import com.app.model.enums.TicketType;
import com.app.repository.*;
import com.app.service.mappers.GetMappers;
import com.app.validator.TicketServiceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final FilmShowRepository filmShowRepository;
    private final MovieRepository movieRepository;
    private final RepertoireRepository repertoireRepository;
    private final TicketServiceValidator ticketValidator;
    private final CinemaRepository cinemaRepository;

    private final JavaMailSender javaMailSender;

  /*  public Set<GetTicketDto> buyTickets(CreateTicketDto ticket) {
        if (Objects.isNull(ticket)) {
            throw new AppException("ticket is null");
        }

        Map<String, String> errors = ticketValidator.validate(ticket);

        if (ticketValidator.hasErrors()) {
            errors.forEach((k, v) -> System.out.println(k + " " + v));
            throw new AppException("Ticket is not valid");
        }

        if (movieRepository.findById(ticket.getMovieId()).isEmpty()) {
            throw new AppException("Movie with given title doesn't exist");
        }

        User user = userRepository.findById(ticket.getUserId())
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));


        List<Repertoire> repertoire = repertoireRepository.findByDateAndCinema_Id(ticket.getDate(), ticket.get)
                .orElseThrow(() -> new AppException("Repertoire with given date and city doesn't exist"));

        FilmShow filmShow = repertoire
                .stream()
                .flatMap(r -> r.getFilmShows()
                        .stream())
                .collect(Collectors.toList())
                .stream()
                .filter(filmS -> ticket.getStartTime().equals(filmS.getStartTime())
                        && ticket.getMovieId().equals(filmS.getMovie().getId()))
                .findFirst()
                .orElseThrow(() -> new AppException("Film Show doesn't exist"));

        // do filmshow dodac sale z miejscami (relacja sala - miejsca)
        //udostepnic sale z miejscami
        Set<Ticket> tickets = saveAllTickets(ticket.getQuantity(), filmShow, user);

        user.setTickets(tickets);
        userRepository.save(user);

        filmShow.setTicketsAvailable(filmShow.getTicketsAvailable() - ticket.getQuantity());
        filmShowRepository.save(filmShow);



        return tickets
                .stream()
                .map(GetMappers::fromTicketToGetTicketDto)
                .collect(Collectors.toSet());
    }*/

    //schedule sprawdzic

    private Set<Ticket> saveAllTickets(int quantity, FilmShow filmShow, User user) {
        if (filmShow.getTicketsAvailable() < quantity) {
            throw new AppException("There is not enough available seats");
        }

        Set<Ticket> tickets = new HashSet<>();

        for (int i = 0; i < quantity; i++) {
            Ticket ticket1 = Ticket.builder()
                    .ticketType(TicketType.NORMAL)
                    .filmShow(filmShow)
                    .cinema(filmShow.getCinemaHall().getCinema())
                    .user(user)
                    .price(TicketType.NORMAL.getPrice())
                    .build();

            tickets.add(ticket1);
        }
        ticketRepository.saveAll(tickets);
        return tickets;
    }

    public String reserveTicket(Long userId, int quantity) {
        return null;
    }

    public List<GetHistoryTicket> getHistoryOfTickets(Long userId, Boolean sendMail, Boolean getFile) {
        if (Objects.isNull(userId)) {
            throw new AppException("id is null");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException("User with given id doesn't exist"));

        String recipientAddress = user.getEmail();
        String subject = "Tickets history";

        List<GetHistoryTicket> tickets = new ArrayList<>();

        user.getTickets().forEach(t -> {
            tickets.add(GetHistoryTicket.builder()
                    .price(t.getPrice())
                    .ticketType(t.getTicketType())
                    .filmShow(t.getFilmShow())
                    .build());
        });

        if (sendMail) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(recipientAddress);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(tickets.toString());
            javaMailSender.send(simpleMailMessage);
        }

        if (getFile) {
            Scanner sc = new Scanner(System.in);
            String fileName = sc.nextLine();
            FileService.saveToFile(fileName, tickets.toString());
        }
        return tickets;
    }
    //zmienic na Enum sendMail, getFile zrobić i jesli kolekcja jestp usta to nie wysle proste
    public List<GetHistoryTicket> getHistoryOfTicketsByPrice(BigDecimal from, BigDecimal to, Long userId, Boolean sendMail, Boolean getFile) {
        if (Objects.isNull(userId)) {
            throw new AppException("id is null");
        }
        if (Objects.isNull(from) || from.compareTo(BigDecimal.ZERO) <= 0) {
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

        String recipientAddress = user.getEmail();
        String subject = "Tickets history from ";

        List<GetHistoryTicket> tickets = new ArrayList<>();

        user.getTickets()
                .stream()
                .filter(p -> p.getPrice().compareTo(from) > 0 && p.getPrice().compareTo(to) < 0)
                .forEach(t -> {
                    tickets.add(GetHistoryTicket.builder()
                            .price(t.getPrice())
                            .ticketType(t.getTicketType())
                            .filmShow(t.getFilmShow())
                            .build());
                });

        if (sendMail) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(recipientAddress);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(tickets.toString());
            javaMailSender.send(simpleMailMessage);
        }

        if (getFile) {
            Scanner sc = new Scanner(System.in);
            String fileName = sc.nextLine();
            FileService.saveToFile(fileName, tickets.toString());
        }

        return tickets;
    }

    public List<GetHistoryTicket> getHistoryOfTicketsByDate(LocalDate from, LocalDate to, Long userId, Boolean sendMail, Boolean getFile) {
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

        String recipientAddress = user.getEmail();
        String subject = "Tickets history";

        List<GetHistoryTicket> tickets = new ArrayList<>();

        user.getTickets()
                .stream()
                .filter(p -> p.getFilmShow().getRepertoire().getDate().compareTo(from) > 0 && p.getFilmShow().getRepertoire().getDate().compareTo(to) < 0)
                .forEach(t -> {
                    tickets.add(GetHistoryTicket.builder()
                            .price(t.getPrice())
                            .ticketType(t.getTicketType())
                            .filmShow(t.getFilmShow())
                            .build());
                });

        if (sendMail) {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(recipientAddress);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(tickets.toString());
            javaMailSender.send(simpleMailMessage);
        }

        if (getFile) {
            Scanner sc = new Scanner(System.in);
            String fileName = sc.nextLine();
            FileService.saveToFile(fileName, tickets.toString());
        }

        return tickets;
    }


}
