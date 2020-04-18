package com.app.controller;

import com.app.dto.createDto.*;
import com.app.dto.data.Info;
import com.app.dto.getDto.GetHistoryTicketDto;
import com.app.dto.getDto.GetTicketDto;
import com.app.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/getAll")
    public ResponseEntity<Info<List<GetTicketDto>>> getAll() {
        return ResponseEntity.ok(Info.<List<GetTicketDto>>builder()
                .data(ticketService.getAll())
                .build());
    }

    @PostMapping("/buySingleTicket")
    public ResponseEntity<Info<String>> buySingleTicket(@RequestBody CreateTicketDto createTicketDto) {
        return new ResponseEntity<>(Info.<String>builder()
                .data(ticketService.buySingleTicket(createTicketDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PostMapping("/buyTickets")
    public ResponseEntity<Info<String>> buyTickets(@RequestBody List<CreateTicketDto> createTicketDtoList) {
        return new ResponseEntity<>(Info.<String>builder()
                .data(ticketService.buyTickets(createTicketDtoList))
                .build(),
                HttpStatus.CREATED);
    }

    @PostMapping("/reserve/{userId}")
    public ResponseEntity<Info<String>> reserveTicket(@PathVariable Long userId, @RequestBody Set<CreateReservationDto> createReservationDto) {
        return new ResponseEntity<>(Info.<String>builder()
                .data(ticketService.reserveTicket(userId, createReservationDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PostMapping("/historyTickets")
    public ResponseEntity<Info<List<GetHistoryTicketDto>>> getHistoryTickets(@RequestBody CreateHistoryTicketDto history) {
        return ResponseEntity.ok(Info.<List<GetHistoryTicketDto>>builder()
                .data(ticketService.getHistoryOfTickets(history))
                .build());
    }

    @PostMapping("/historyByPrice")
    public ResponseEntity<Info<List<GetHistoryTicketDto>>> getHistoryTicketsByPrice(@RequestBody CreateHistoryByPriceDto history) {

        return ResponseEntity.ok(Info.<List<GetHistoryTicketDto>>builder()
                .data(ticketService.getHistoryOfTicketsByPrice(history))
                .build());
    }

    @PostMapping("/historyByDate")
    public ResponseEntity<Info<List<GetHistoryTicketDto>>> getHistoryTicketsByDate(@RequestBody CreateHistoryByDateDto history) {

        return ResponseEntity.ok(Info.<List<GetHistoryTicketDto>>builder()
                .data(ticketService.getHistoryOfTicketsByDate(history))
                .build());
    }


}
