package com.app.controller;

import com.app.dto.createDto.CreateReservationDto;
import com.app.dto.data.Info;
import com.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/reserve/{userId}")
    public ResponseEntity<Info<String>> reserveTicket(@PathVariable Long userId,  @RequestBody Set<CreateReservationDto> createReservationDto) {
        return new ResponseEntity<>(Info.<String>builder()
                .data(reservationService.reserveTicket(userId, createReservationDto))
                .build(),
                HttpStatus.CREATED);
    }
}
