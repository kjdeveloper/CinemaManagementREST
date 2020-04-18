package com.app.service;

import com.app.dto.getDto.GetMovieDto;
import com.app.exception.AppException;
import com.app.model.Ticket;
import com.app.model.enums.City;
import com.app.model.TicketType;
import com.app.repository.TicketRepository;
import com.app.service.file.FileService;
import com.app.service.mail.MailService;
import com.app.service.mappers.GetMappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsService {

    private final TicketRepository ticketRepository;
    private final MailService mailService;

    public Map<City, Long> getCityWithHighestNumberOfWatcher(Map<String, Boolean> mailFileChoices) {
        Map<City, Long> collect = ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(ti -> ti.getCinema().getCity(),
                        Collectors.counting()));

        if (mailFileChoices.get("sendMail")) {
            mailService.sendEmail("City with highest number of watcher", collect);
        }

        if (mailFileChoices.get("saveToFile")) {
            FileService.saveToPDFFile("City with highest number of watcher", collect);
        }

        return collect;
    }

    public Map<City, GetMovieDto> getTheMostPopularMovieInCity(Map<String, Boolean> mailFileChoices) {
        final LinkedHashMap<City, GetMovieDto> collect = ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(
                        t -> t.getCinema().getCity(),
                        Collectors.collectingAndThen(Collectors.groupingBy(t -> t.getFilmShow().getMovie(), Collectors.counting()),
                                m -> m.entrySet()
                                        .stream().min(Comparator.comparingLong(Map.Entry::getValue))
                                        .orElseThrow(() -> new AppException("No movie"))
                                        .getKey())))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> GetMappers.fromMovieToGetMovieDto(v.getValue()),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));

        if (mailFileChoices.get("sendMail")) {
            mailService.sendEmail("Most popular movie in the city", collect);
        }

        if (mailFileChoices.get("saveToFile")) {
            FileService.saveToFile("Most popular movie in the city", collect);
        }
        return collect;
    }

    public Map<City, BigDecimal> averagePriceOfTicketsInCity(Map<String, Boolean> mailFileChoices) {
        final LinkedHashMap<City, BigDecimal> collect = ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(t -> t.getCinema().getCity(),
                        Collectors.toList()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> v.getValue()
                                .stream()
                                .map(Ticket::getTicketType)
                                .map(TicketType::getPrice)
                                .reduce(BigDecimal.ONE, BigDecimal::add)
                                .divide(BigDecimal.valueOf(v.getValue().size())),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));

        if (mailFileChoices.get("sendMail")) {
            mailService.sendEmail("Average price of tickets in city", collect);
        }

        if (mailFileChoices.get("saveToFile")) {
            FileService.saveToFile("Average price of tickets in city", collect);
        }
        return collect;
    }

    public Map<City, BigDecimal> profitForEveryCity(Map<String, Boolean> mailFileChoices) {
        final LinkedHashMap<City, BigDecimal> collect = ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(t -> t.getCinema().getCity(),
                        Collectors.toList()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> v.getValue()
                                .stream()
                                .map(Ticket::getTicketType)
                                .map(TicketType::getPrice)
                                .reduce(BigDecimal.ONE, BigDecimal::add),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));

        if (mailFileChoices.get("sendMail")) {
            mailService.sendEmail("Profit for each city", collect);
        }

        if (mailFileChoices.get("saveToFile")) {
            FileService.saveToFile("Profit for each city", collect);
        }
        return collect;
    }

    public Map<String, Long> mostCommonTicketType(Map<String, Boolean> mailFileChoices) {
        final Map<String, Long> collect = ticketRepository.findAll()
                .stream()
                .map(Ticket::getTicketType)
                .collect(Collectors.groupingBy(TicketType::getName,
                        Collectors.counting()));

        if (mailFileChoices.get("sendMail")) {
            mailService.sendEmail("Most common ticket type", collect);
        }

        if (mailFileChoices.get("saveToFile")) {
            FileService.saveToFile("Most common ticket type", collect);
        }
        return collect;
    }

    public Map<DayOfWeek, Long> dayWithTheMostVisits(Map<String, Boolean> mailFileChoices) {
        final Map<DayOfWeek, Long> collect = ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(ti -> ti.getFilmShow().getStartTime().getDayOfWeek(),
                        Collectors.counting()));

        if (mailFileChoices.get("sendMail")) {
            mailService.sendEmail("Day with the most visits", collect);
        }

        if (mailFileChoices.get("saveToFile")) {
            FileService.saveToFile("Day with the most visits", collect);
        }
        return collect;
    }


}