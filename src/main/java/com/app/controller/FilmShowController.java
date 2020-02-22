package com.app.controller;

import com.app.dto.createDto.CreateFilmShowDto;
import com.app.dto.data.Info;
import com.app.dto.getDto.GetFilmShowDto;
import com.app.service.FilmShowService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.info.InfoProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filmShow")
public class FilmShowController {

    private final FilmShowService filmShowService;

    @GetMapping
    public ResponseEntity<Info<List<GetFilmShowDto>>> getAll() {
        return ResponseEntity.ok(Info.<List<GetFilmShowDto>>builder()
                .data(filmShowService.findAll())
                .build());
    }

    @GetMapping("/{cinemaId}")
    public ResponseEntity<Info<List<GetFilmShowDto>>> getAllInParticularCinema(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(Info.<List<GetFilmShowDto>>builder()
                .data(filmShowService.findAllFilmShowsInParticularCinema(cinemaId))
                .build());
    }

    @GetMapping("/{title}")
    public ResponseEntity<Info<List<GetFilmShowDto>>> getAllByMovieTitle(@PathVariable String title) {
        return ResponseEntity.ok(Info.<List<GetFilmShowDto>>builder()
                .data(filmShowService.findFilmShowsByMovieTitle(title))
                .build());
    }

    @PostMapping("/{cinemaId}")
    public ResponseEntity<Info<Long>> add(@PathVariable Long cinemaId, @RequestBody CreateFilmShowDto filmShowDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(filmShowService.add(cinemaId, filmShowDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("{filmShowId}")
    public ResponseEntity<Info<Long>> update(@PathVariable Long filmShowId, @RequestBody CreateFilmShowDto filmShowDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(filmShowService.edit(filmShowId, filmShowDto))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{filmShowId}")
    public ResponseEntity<Info<Long>> deleteById(@PathVariable Long filmShowId) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(filmShowService.deleteById(filmShowId))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/all")
    public ResponseEntity<Info<Long>> deleteAll() {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(filmShowService.deleteAll())
                .build(),
                HttpStatus.OK);
    }
}
