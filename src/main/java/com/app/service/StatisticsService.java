package com.app.service;

import com.app.dto.getDto.GetMovieDto;
import com.app.exception.AppException;
import com.app.model.Ticket;
import com.app.model.enums.City;
import com.app.model.enums.TicketType;
import com.app.repository.TicketRepository;
import com.app.service.mappers.GetMappers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsService {

    private final TicketRepository ticketRepository;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public Map<City, Long> getCityWithHighestNumberOfWatcher() throws IOException {
        final Map<City, Long> collect = ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(ti -> ti.getCinema().getCity(),
                        Collectors.counting()));

        gson.toJson(collect, new FileWriter("citiesWithNumberOfWatcherStatistics"));
        return collect;
    }

    public Map<City, GetMovieDto> getTheMostPopularMovieInCity() {
        return ticketRepository.findAll()
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
    }

    public Map<City, BigDecimal> averagePriceOfTicketsInCity() {
        return ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(t -> t.getCinema().getCity(),
                        Collectors.toList()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> v.getValue()
                                .stream()
                                .map(Ticket::getPrice)
                                .reduce(BigDecimal.ONE, BigDecimal::add)
                                .divide(BigDecimal.valueOf(v.getValue().size())),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    public Map<City, BigDecimal> profitForEveryCity() {
        return ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(t -> t.getCinema().getCity(),
                        Collectors.toList()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        v -> v.getValue()
                                .stream()
                                .map(Ticket::getPrice)
                                .reduce(BigDecimal.ONE, BigDecimal::add),
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }

    public Map<TicketType, Long> mostCommonTicketType() {
        return ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Ticket::getTicketType,
                        Collectors.counting()));
    }

    public Map<DayOfWeek, Long> dayWithTheMostVisits() {
        return ticketRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(ti -> ti.getFilmShow().getStartTime().getDayOfWeek(),
                        Collectors.counting()));
    }


}