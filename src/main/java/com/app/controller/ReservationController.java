package com.app.controller;

import com.app.dto.createDto.CreateReservationDto;
import com.app.dto.data.Info;
import com.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reserve")
    public ResponseEntity<Info<String>> reserveTicket(@RequestBody CreateReservationDto createReservationDto) {
        return new ResponseEntity<>(Info.<String>builder()
                .data(reservationService.reserveTicket(createReservationDto))
                .build(),
                HttpStatus.CREATED);
    }
}
