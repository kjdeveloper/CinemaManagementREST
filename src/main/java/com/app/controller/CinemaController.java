package com.app.controller;

import com.app.dto.createDto.CreateCinemaDto;
import com.app.dto.data.Info;
import com.app.dto.getDto.GetCinemaDto;
import com.app.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jmx.export.naming.IdentityNamingStrategy;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinema")
public class CinemaController {

    private final CinemaService cinemaService;

    @GetMapping("/findAll")
    public ResponseEntity<Info<List<GetCinemaDto>>> getAll() {
        return ResponseEntity.ok(Info.<List<GetCinemaDto>>builder()
                .data(cinemaService.findAll())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Info<GetCinemaDto>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(Info.<GetCinemaDto>builder()
                .data(cinemaService.findById(id))
                .build());
    }

    @PostMapping
    public ResponseEntity<Info<Long>> add(@RequestBody CreateCinemaDto cinemaDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(cinemaService.add(cinemaDto))
                .build(),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Info<Long>> update(@PathVariable Long id, @RequestBody CreateCinemaDto cinemaDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(cinemaService.update(id, cinemaDto))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Info<Long>> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(cinemaService.deleteById(id))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Info<Long>> deleteAll() {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(cinemaService.deleteAll())
                .build(),
                HttpStatus.OK);
    }


}
