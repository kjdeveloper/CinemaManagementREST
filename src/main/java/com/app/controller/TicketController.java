package com.app.controller;

import com.app.dto.data.Info;
import com.app.dto.getDto.GetHistoryTicket;
import com.app.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/history/{userId}")
    public ResponseEntity<Info<List<GetHistoryTicket>>> getHistoryTickets(@PathVariable Long userId) {
        return ResponseEntity.ok(Info.<List<GetHistoryTicket>>builder()
                .data(ticketService.getHistoryOfTickets(userId, false, false))
                .build());
    }

    @PostMapping("/history")
    public ResponseEntity<Info<List<GetHistoryTicket>>> getHistoryTicketsByPrice(@RequestBody Long userId,
                                                                                 @RequestBody BigDecimal from,
                                                                                 @RequestBody BigDecimal to,
                                                                                 @RequestBody boolean sendMail,
                                                                                 @RequestBody boolean getFile) {

        return ResponseEntity.ok(Info.<List<GetHistoryTicket>>builder()
                .data(ticketService.getHistoryOfTicketsByPrice(from, to, userId, sendMail, getFile))
                .build());
    }

    @GetMapping("/historyByDate")
    public ResponseEntity<Info<List<GetHistoryTicket>>> getHistoryTicketsByDate(@RequestBody Long userId,
                                                                                @RequestBody LocalDate from,
                                                                                @RequestBody LocalDate to,
                                                                                @RequestBody boolean sendMail,
                                                                                @RequestBody boolean getFile) {

        return ResponseEntity.ok(Info.<List<GetHistoryTicket>>builder()
                .data(ticketService.getHistoryOfTicketsByDate(from, to, userId, sendMail, getFile))
                .build());
    }


}
