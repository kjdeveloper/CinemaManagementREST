package com.app.controller;

import com.app.dto.createDto.CreateCinemaHallDto;
import com.app.dto.data.Info;
import com.app.dto.getDto.GetCinemaHallDto;
import com.app.service.CinemaHallService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cinemaHall")
public class CinemaHallController {

    private final CinemaHallService cinemaHallService;

    @GetMapping("/findAll")
    public ResponseEntity<Info<List<GetCinemaHallDto>>> getAll(){
        return ResponseEntity.ok(Info.<List<GetCinemaHallDto>>builder()
                .data(cinemaHallService.findAll())
                .build());
    }

    @GetMapping("/byCinemaId/{cinemaId}")
    public ResponseEntity<Info<List<GetCinemaHallDto>>> getAllHallsInParticularCinema(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(Info.<List<GetCinemaHallDto>>builder()
                .data(cinemaHallService.getAllHallsInParticularCinema(cinemaId))
                .build());
    }

    @GetMapping("/byName/{name}")
    public ResponseEntity<Info<List<GetCinemaHallDto>>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(Info.<List<GetCinemaHallDto>>builder()
                .data(cinemaHallService.getByName(name))
                .build());
    }

    @PostMapping("/addCinemaHallToCinema/{cinemaId}")
    public ResponseEntity<Info<Long>> add(@PathVariable Long cinemaId, @RequestBody CreateCinemaHallDto cinemaHallDto) {
        return new ResponseEntity<>(Info.<Long>builder()
                .data(cinemaHallService.add(cinemaId, cinemaHallDto))
                .build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Info<Long>> update(@PathVariable Long id, @RequestBody CreateCinemaHallDto cinemaHallDto){
        return new ResponseEntity<>(Info.<Long>builder()
                .data(cinemaHallService.update(id, cinemaHallDto))
                .build(),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Info<Long>> deleteById(@PathVariable Long id){
        return new ResponseEntity<>(Info.<Long>builder()
                .data(cinemaHallService.deleteById(id))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Info<Long>> deleteAll(){
        return new ResponseEntity<>(Info.<Long>builder()
                .data(cinemaHallService.deleteAll())
                .build(),
                HttpStatus.OK);
    }
}
