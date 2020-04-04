package com.app.controller;

import com.app.dto.createDto.CreateTicketTypeDto;
import com.app.dto.data.Info;
import com.app.dto.getDto.GetTicketTypeDto;
import com.app.service.TicketTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticketType")
public class TicketTypeController {
    private final TicketTypeService ticketTypeService;

    @GetMapping("/getAll")
    public ResponseEntity<Info<List<GetTicketTypeDto>>> getAll() {
        return ResponseEntity.ok(Info.<List<GetTicketTypeDto>>builder()
                .data(ticketTypeService.findAll())
                .build());
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<Info<GetTicketTypeDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Info.<GetTicketTypeDto>builder()
                .data(ticketTypeService.findById(id))
                .build());
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<Info<GetTicketTypeDto>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(Info.<GetTicketTypeDto>builder()
                .data(ticketTypeService.findByName(name))
                .build());
    }

    @PostMapping("/add")
    public ResponseEntity<Info<Long>> add(@RequestBody CreateTicketTypeDto createTicketTypeDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(ticketTypeService.addTicketType(createTicketTypeDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Info<Long>> update(@PathVariable Long id, @RequestBody CreateTicketTypeDto createTicketTypeDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(ticketTypeService.updateTicket(id, createTicketTypeDto))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/one/{id}")
    public ResponseEntity<Info<Long>> deleteOne(@PathVariable Long id) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(ticketTypeService.deleteById(id))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Info<Long>> deleteAll() {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(ticketTypeService.deleteAll())
                .build(),
                HttpStatus.OK);
    }


}
