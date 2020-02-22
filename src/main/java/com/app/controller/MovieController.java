package com.app.controller;

import com.app.dto.createDto.CreateMovieDto;
import com.app.dto.data.Info;
import com.app.dto.getDto.GetMovieDto;
import com.app.model.enums.Genre;
import com.app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<Info<List<GetMovieDto>>> getAllMovies() {
        return ResponseEntity.ok(Info.<List<GetMovieDto>>builder()
                .data(movieService.findAll())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Info<GetMovieDto>> update(@PathVariable Long id) {
        return new ResponseEntity<>(Info.<GetMovieDto>builder()
                .data(movieService.findById(id))
                .build(),
                HttpStatus.OK);
    }

    @GetMapping("/{genre}")
    public ResponseEntity<Info<List<GetMovieDto>>> getAllMoviesByGenre(@PathVariable Genre genre) {
        return ResponseEntity.ok(Info.<List<GetMovieDto>>builder()
                .data(movieService.findByGenre(genre))
                .build());
    }

    @GetMapping("/{title}")
    public ResponseEntity<Info<List<GetMovieDto>>> getAllMoviesByTitle(@PathVariable String title) {
        return ResponseEntity.ok(Info.<List<GetMovieDto>>builder()
                .data(movieService.findByTitle(title))
                .build());
    }

    @GetMapping("/betweenDates")
    public ResponseEntity<Info<List<GetMovieDto>>> getAllMovies(@RequestBody LocalDate from, @RequestBody LocalDate to) {
        return ResponseEntity.ok(Info.<List<GetMovieDto>>builder()
                .data(movieService.findByDateBetweenGiven(from, to))
                .build());
    }

    @PostMapping("/addMovie")
    public ResponseEntity<Info<Long>> addOne(@RequestBody CreateMovieDto movie) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(movieService.addOne(movie))
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Info<Long>> update(@PathVariable Long id, @RequestBody CreateMovieDto movie) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(movieService.update(id, movie))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Info<Long>> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(movieService.deleteById(id))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Info<Long>> deleteAll() {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(movieService.deleteAll())
                .build(),
                HttpStatus.OK);
    }

    @PostMapping("/addToFavourite/{userId}/{movieId}")
    public ResponseEntity<Info<Long>> addToFavourites(@PathVariable Long userId, @PathVariable Long movieId) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(movieService.addToFavourites(userId, movieId))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{movieId}")
    public ResponseEntity<Info<Long>> deleteMovieFromFavourites(@PathVariable Long userId, @PathVariable Long movieId) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(movieService.deleteFromFavourites(userId, movieId))
                .build(),
                HttpStatus.OK);
    }
}
