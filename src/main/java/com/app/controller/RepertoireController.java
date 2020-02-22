package com.app.controller;

import com.app.dto.createDto.CreateRepertoireDto;
import com.app.dto.data.Info;
import com.app.dto.getDto.GetRepertoireDto;
import com.app.model.enums.City;
import com.app.service.RepertoireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/repertoire")
public class RepertoireController {

    private final RepertoireService repertoireService;

    @GetMapping("/byDateAndCinema")
    public ResponseEntity<Info<List<GetRepertoireDto>>> getAllByDateAndCity(@RequestBody LocalDate date, @RequestBody City city) {
        return ResponseEntity.ok(Info.<List<GetRepertoireDto>>builder()
                .data(repertoireService.findByDateAndCinemaCity(date, city))
                .build());
    }

    @GetMapping
    public ResponseEntity<Info<List<GetRepertoireDto>>> getAll() {
        return ResponseEntity.ok(Info.<List<GetRepertoireDto>>builder()
                .data(repertoireService.findAll())
                .build());
    }

    @PostMapping
    public ResponseEntity<Info<Long>> add(@RequestBody CreateRepertoireDto repertoireDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(repertoireService.add(repertoireDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Info<Long>> update(@PathVariable Long id, @RequestBody CreateRepertoireDto repertoireDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(repertoireService.update(id, repertoireDto))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Info<Long>> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(repertoireService.deleteById(id))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Info<Long>> deleteAll() {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(repertoireService.deleteAll())
                .build(),
                HttpStatus.OK);
    }
}
