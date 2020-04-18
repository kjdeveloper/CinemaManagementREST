package com.app.controller;

import com.app.dto.data.Info;
import com.app.dto.getDto.GetMovieDto;
import com.app.model.enums.City;
import com.app.model.TicketType;
import com.app.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<Info<Map<City, Long>>> getMapOfWatchersInTheCity(@RequestBody Map<String, Boolean> mapOfChoices) {
        return ResponseEntity.ok(Info.<Map<City, Long>>builder()
                .data(statisticsService.getCityWithHighestNumberOfWatcher(mapOfChoices))
                .build());
    }

    @GetMapping("/theMostPopMovInCity")
    public ResponseEntity<Info<Map<City, GetMovieDto>>> getMostPopularMovieInCity(@RequestBody Map<String, Boolean> mapOfChoices) {
        return ResponseEntity.ok(Info.<Map<City, GetMovieDto>>builder()
                .data(statisticsService.getTheMostPopularMovieInCity(mapOfChoices))
                .build());
    }

    @GetMapping("/avePriceOfTickets")
    public ResponseEntity<Info<Map<City, BigDecimal>>> getAveragePriceOfTickets(@RequestBody Map<String, Boolean> mapOfChoices) {
        return ResponseEntity.ok(Info.<Map<City, BigDecimal>>builder()
                .data(statisticsService.averagePriceOfTicketsInCity(mapOfChoices))
                .build());
    }

    @GetMapping("/profitForCity")
    public ResponseEntity<Info<Map<City, BigDecimal>>> getProfitForCity(@RequestBody Map<String, Boolean> mapOfChoices) {
        return ResponseEntity.ok(Info.<Map<City, BigDecimal>>builder()
                .data(statisticsService.profitForEveryCity(mapOfChoices))
                .build());
    }

    @GetMapping("/mostCommonTicketType")
    public ResponseEntity<Info<Map<String, Long>>> getMostCommonTicketType(@RequestBody Map<String, Boolean> mapOfChoices) {
        return ResponseEntity.ok(Info.<Map<String, Long>>builder()
                .data(statisticsService.mostCommonTicketType(mapOfChoices))
                .build());
    }

    @GetMapping("/dayWithTheMostVisits")
    public ResponseEntity<Info<Map<DayOfWeek, Long>>> getDayWithTheMostVisits(@RequestBody Map<String, Boolean> mapOfChoices) {
        return ResponseEntity.ok(Info.<Map<DayOfWeek, Long>>builder()
                .data(statisticsService.dayWithTheMostVisits(mapOfChoices))
                .build());
    }
}
