package com.app.controller;

import com.app.dto.data.Info;
import com.app.dto.getDto.GetMovieDto;
import com.app.model.enums.City;
import com.app.model.enums.TicketType;
import com.app.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stats")
public class StatsController {

    private final StatisticsService statisticsService;

    @GetMapping("/cityWithHighestNumOfWatcher")
    public ResponseEntity<Info<Map<City, Long>>> getMapOfWatchersInTheCity() {
        return ResponseEntity.ok(Info.<Map<City, Long>>builder()
                .data(statisticsService.getCityWithHighestNumberOfWatcher())
                .build());
    }

    @GetMapping("/theMostPopMovInCity")
    public ResponseEntity<Info<Map<City, GetMovieDto>>> getMostPopularMovieInCity() {
        return ResponseEntity.ok(Info.<Map<City, GetMovieDto>>builder()
                .data(statisticsService.getTheMostPopularMovieInCity())
                .build());
    }

    @GetMapping("/avePriceOfTickets")
    public ResponseEntity<Info<Map<City, BigDecimal>>> getAveragePriceOfTickets() {
        return ResponseEntity.ok(Info.<Map<City, BigDecimal>>builder()
                .data(statisticsService.averagePriceOfTicketsInCity())
                .build());
    }

    @GetMapping("/profitForCity")
    public ResponseEntity<Info<Map<City, BigDecimal>>> getProfitForCity() {
        return ResponseEntity.ok(Info.<Map<City, BigDecimal>>builder()
                .data(statisticsService.profitForEveryCity())
                .build());
    }

    @GetMapping("/mostCommonTicketType")
    public ResponseEntity<Info<Map<TicketType, Long>>> getMostCommonTicketType() {
        return ResponseEntity.ok(Info.<Map<TicketType, Long>>builder()
                .data(statisticsService.mostCommonTicketType())
                .build());
    }

    @GetMapping("/dayWithTheMostVisits")
    public ResponseEntity<Info<Map<DayOfWeek, Long>>> getDayWithTheMostVisits() {
        return ResponseEntity.ok(Info.<Map<DayOfWeek, Long>>builder()
                .data(statisticsService.dayWithTheMostVisits())
                .build());
    }


}
