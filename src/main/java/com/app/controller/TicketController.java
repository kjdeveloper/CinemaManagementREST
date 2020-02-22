package com.app.controller;

import com.app.dto.data.Info;
import com.app.dto.getDto.GetHistoryTicket;
import com.app.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping("/history/{from}/{to}/{userId}/{sendMail}/{getFile}")
    public ResponseEntity<Info<List<GetHistoryTicket>>> getHistoryTicketsByPrice(@PathVariable Long userId,
                                                                                 @PathVariable BigDecimal from,
                                                                                 @PathVariable BigDecimal to,
                                                                                 @PathVariable(name = "sendMail", required = false) boolean sendMail,
                                                                                 @PathVariable(name = "getFile", required = false) boolean getFile) {

        boolean sendMailFinal = StringUtils.isEmpty(sendMail) ? false : sendMail;
        boolean getFileFinal = StringUtils.isEmpty(getFile) ? false : getFile;

        return ResponseEntity.ok(Info.<List<GetHistoryTicket>>builder()
                .data(ticketService.getHistoryOfTicketsByPrice(from, to, userId, sendMailFinal, getFileFinal))
                .build());
    }



}
